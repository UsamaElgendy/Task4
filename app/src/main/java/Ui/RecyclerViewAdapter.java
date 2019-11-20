package Ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.task4.R;

import java.util.List;

import data.DatabaseFaculty;
import model.Register;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Register> registerList;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;



    public RecyclerViewAdapter(Context context, List<Register> registerList) {
        this.context = context;
        this.registerList = registerList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view, context); // return to a viewHolder method // it contract with ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Register grocery = registerList.get(position);

        holder.txt_studentName.setText(grocery.getStudentName());
        holder.txt_email.setText(grocery.getPassword());
        holder.txt_phone.setText(grocery.getEmail());

    }

    @Override
    public int getItemCount() {
        return registerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_studentName;
        public TextView txt_email;
        public TextView txt_phone;

        public Button editButton , deleteButton ;


        public int id;  // it holding a id of each item

        public ViewHolder(@NonNull View view, final Context ctx) {
            super(view);
            context = ctx;
            txt_studentName = view.findViewById(R.id.txt_studentName);
            txt_email = view.findViewById(R.id.txt_email);
            txt_phone = view.findViewById(R.id.txt_phone);


            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
        // here we can delete and edit when click button
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editButton:
                    int position = getAdapterPosition();
                    Register register = registerList.get(position);
                    editItem(register);
                    break;
                case R.id.deleteButton:
                    position = getAdapterPosition();
                    register = registerList.get(position);
                    deleteItem(register.getId());
                    break;
            }
        }


        public void editItem(final Register register) {
            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            final TextView tile = view.findViewById(R.id.tile);
            final EditText editText_userName = view.findViewById(R.id.editText_userName);
            final EditText editText_Email = view.findViewById(R.id.editText_Email);
            final EditText editText_Password = view.findViewById(R.id.editText_Password);
            final EditText editText_Phone = view.findViewById(R.id.editText_Phone);


//            final Spinner spinnerFacultyName = view.findViewById(R.id.spinnerFacultyName);
//            final Spinner spinnerDepartmentName = view.findViewById(R.id.spinnerDepartmentName);

            tile.setText("Edit Grocery");
            Button saveButton = view.findViewById(R.id.saveButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseFaculty db = new DatabaseFaculty(context);

                    // Update item

                    register.setStudentName(editText_userName.getText().toString());
                    register.setEmail(editText_Email.getText().toString());
                    register.setPassword(editText_Password.getText().toString());
                    register.setPhone(editText_Phone.getText().toString());


//                    register.setFacultyName(spinnerFacultyName.getSelectedItem().toString());
//                    register.setDepartmentName(spinnerDepartmentName.getSelectedItem().toString());

                    //TODO VALIDATION HERE


                        db.updateUser(register);

                        //this will go to notify other class and other listener something happen here

                        notifyItemChanged(getAdapterPosition(), register); // it come with recycler view adapter
                    dialog.dismiss();


                }
            });

        }



        public void deleteItem(final int id) {
            // we need make popup dialog for check if user need to delete a data for database
            // we make a layout dialog

            // Create a  alertDialog
            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confiramtion_dialog, null);
            Button noButton = view.findViewById(R.id.noButton);
            Button yesButton = view.findViewById(R.id.yesButton);
            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete the item from database

                    DatabaseFaculty db = new DatabaseFaculty(context);
                    //delete item
                    db.deleteUser(id);// it passed in parameter method

                    // to make sure that this removal reflacted
                    registerList.remove(getAdapterPosition());
                    //notify that the item removed
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });
        }

    }
}
