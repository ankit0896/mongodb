package com.cheezyfoody.mongodbapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;
import org.bson.types.ObjectId;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.et_user_firstName)
    EditText firstName;
    @BindView(R.id.et_user_lastName)
    EditText lastName;
    @BindView(R.id.et_user_password)
    EditText password;
    @BindView(R.id.et_user_email_id)
    EditText emailId;
    @BindView(R.id.card_submit_btn)
    CardView signUp;

    Realm uiThreadRealm;
    App app;
    Credentials credentials;
    MongoDatabase db;
    String f_name, l_name, mail, pass;

    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        Realm.init(this); // context, usually an Activity or Application
        String appID = "tanklog-wmmph";
        app = new App(new AppConfiguration.Builder(appID)
                .build());
        credentials = Credentials.anonymous();
        signUp.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v == signUp) {
            f_name = firstName.getText().toString();
            l_name = lastName.getText().toString();
            pass = password.getText().toString();
            mail = emailId.getText().toString();
            try {
                addUserToDB(f_name, l_name, pass, mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addUserToDB(String f_name, String l_name, String pass, String mail) {

        mongoClient = new MongoClient("") {
            @Override
            public MongoDatabase getDatabase(String databaseName) {
                return super.getDatabase(databaseName);
            }
        };
        mongoDatabase = mongoClient.getDatabase("tanklog_database");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");
        mongoCollection.insertOne(new Document().append("first_name",f_name).append("last_name",l_name).append("email_id",mail).append("password",pass)).getAsync(result -> {
            if(result.isSuccess())
            {
                Toast.makeText(this, "Created Successfully User", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Created Successfully User", Toast.LENGTH_SHORT).show();
            }
        });
    }

}