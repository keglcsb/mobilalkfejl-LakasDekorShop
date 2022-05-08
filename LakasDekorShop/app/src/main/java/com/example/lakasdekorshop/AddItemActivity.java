package com.example.lakasdekorshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import model.Item;

public class AddItemActivity extends AppCompatActivity {

    EditText title;
    EditText desc;
    EditText cost;
    NotificationHandler handler;
    FirebaseFirestore firestore;
    CollectionReference dbItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        title = findViewById(R.id.add_title);
        desc = findViewById(R.id.add_desc);
        cost = findViewById(R.id.add_cost);
        firestore = FirebaseFirestore.getInstance();
        dbItems = firestore.collection("items");
        handler = new NotificationHandler(this);
    }
    public void addItem(View view) {
        String itemTitle = title.getText().toString();
        String itemDesc = desc.getText().toString();
        String itemCost = cost.getText().toString();
        if(itemTitle.equals("") ||itemDesc.equals("") || itemCost.equals("")){
            handler.send("Nem sikerült a terméket hozzáadni");
            return;
        }

        itemCost = itemCost + "Ft";

        Item item = new Item(itemTitle, itemDesc, itemCost, 0);
        dbItems.add(item).addOnSuccessListener(success -> {
            handler.send("Sikeresen hozzáadtad a terméket");
        });
        goBack(view);
    }

    public void goBack(View view) {
        finish();
    }
}