package com.example.ihelpproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterEmployeeFragment extends Fragment {
    private FirebaseAuth mAuth;
    TextInputLayout et_name, et_email, et_username, et_password, et_supervisor, et_companyName, et_age, et_address, et_phonenumber;

    Button btn_create;
    Employees employeeUser;
    DatabaseReference databaseRegisterEmployee;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_register_employee, container, false);
        mAuth = FirebaseAuth.getInstance();

        et_address = view.findViewById(R.id.et_address);
        et_phonenumber = view.findViewById(R.id.et_phonenumber);
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        et_username = view.findViewById(R.id.et_username);
        et_supervisor = view.findViewById(R.id.et_supervisorName);
        et_companyName = view.findViewById(R.id.et_companyName);
        et_age = view.findViewById(R.id.et_age);

        databaseRegisterEmployee = FirebaseDatabase.getInstance().getReference("employeeUser");

        btn_create = view.findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getEditText().getText().toString();
                String password = et_password.getEditText().getText().toString();
                String name = et_name.getEditText().getText().toString();
                String username = et_username.getEditText().getText().toString();
                String address = et_address.getEditText().getText().toString();
                String phoneNumber = et_phonenumber.getEditText().getText().toString();
                String supervisor = et_supervisor.getEditText().getText().toString();
                String companyName = et_companyName.getEditText().getText().toString();
                String age = et_age.getEditText().getText().toString();

                if (validateEmail(email) | validatePassword(password) | validateName(name) | validateUsername(username) | validateAddress(address) | validatePhoneNumber(phoneNumber) |
                        validateSupervisor(supervisor) | validateCompanyName(companyName) | validateAge(age)) {

                    String id = databaseRegisterEmployee.push().getKey();
                    employeeUser = new Employees(id, name, email, username, password, age, address, phoneNumber, "employee", supervisor, companyName);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseDatabase.getInstance().getReference("employeeUser")
                                            .child(mAuth.getCurrentUser().getUid())
                                            .setValue(employeeUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                                startActivity(i);
                                                Toast.makeText(getActivity(), "account created", Toast.LENGTH_LONG).show();
                                            } else {

                                                Toast.makeText(getActivity(), "there is something wrong", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });
                }

            }
        });


        return view;

    }

    private boolean validateAge(String age) {
        if (age.isEmpty()) {
            et_age.setError("field cant be empty");
            return false;
        } else {
            et_age.setError(null);
            et_age.setErrorEnabled(false);
            return true;
        }
    }//end validateAge

    private boolean validateCompanyName(String companyName) {
        if (companyName.isEmpty()) {
            et_companyName.setError("field cant be empty");
            return false;
        } else {
            et_companyName.setError(null);
            et_companyName.setErrorEnabled(false);
            return true;
        }
    }// end validateCompanyName

    private boolean validateUsername(String username) {
        if (username.isEmpty()) {
            et_username.setError("field cant be empty");
            return false;
        } else {
            et_username.setError(null);
            et_username.setErrorEnabled(false);
            return true;
        }
    }//end validateUsername

    private boolean validateSupervisor(String supervisor) {
        if (supervisor.isEmpty()) {
            et_supervisor.setError("field cant be empty");
            return false;
        } else {
            et_supervisor.setError(null);
            et_supervisor.setErrorEnabled(false);
            return true;
        }
    }//end validateSupervisor


    private boolean validateEmail(String email) {

        if (email.isEmpty()) {
            et_email.setError("field cant be empty");
            return false;
        } else {
            et_email.setError(null);
            et_email.setErrorEnabled(false);
            return true;
        }
    }// end validateEmail method.

    private boolean validatePassword(String password) {

        if (password.isEmpty()) {
            et_password.setError("field cant be empty");
            return false;
        } else {
            et_password.setError(null);
            et_password.setErrorEnabled(false);
            return true;
        }
    }// end validatePassword method.

    private boolean validateName(String name) {

        if (name.isEmpty()) {
            et_name.setError("field cant be empty");
            return false;
        } else {
            et_name.setError(null);
            et_name.setErrorEnabled(false);
            return true;
        }
    }// end validateName method.

    private boolean validateAddress(String address) {

        if (address.isEmpty()) {
            et_address.setError("field cant be empty");
            return false;
        } else {
            et_address.setError(null);
            et_address.setErrorEnabled(false);
            return true;
        }
    }// end validateAddress method.

    private boolean validatePhoneNumber(String phoneNumber) {

        if (phoneNumber.isEmpty()) {
            et_phonenumber.setError("field cant be empty");
            return false;
        } else {
            et_phonenumber.setError(null);
            et_phonenumber.setErrorEnabled(false);
            return true;
        }
    }// end validateAddress method.
}
