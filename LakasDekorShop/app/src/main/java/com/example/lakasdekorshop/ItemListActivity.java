package com.example.lakasdekorshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import model.Item;
import model.ItemAdapter;

public class ItemListActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    ArrayList<Item> items;
    ArrayList<Item> cart;
    private ItemAdapter adapter;

    private FirebaseFirestore firestore;
    private CollectionReference dbItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mAuth = FirebaseAuth.getInstance();
        //mAuth.signOut();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        items = new ArrayList<>();

        adapter = new ItemAdapter(this, items);
        mRecyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();
        dbItems = firestore.collection("items");

        //queryData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryData();
    }

    public void queryData(){
        items.clear();

        dbItems.orderBy("name").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                Item item = document.toObject(Item.class);
                item.setId(document.getId());
                items.add(item);
            }
            if(items.size() == 0){
                initializeData();
                queryData();
            }

            adapter.notifyDataSetChanged();
        });
    }

    private void initializeData(){
        String[] itemsList = getResources().getStringArray(R.array.item_names);
        String[] itemsInfo = getResources().getStringArray(R.array.item_descs);
        String[] itemsPrice = getResources().getStringArray(R.array.item_cost);


        for(int i = 0; i< itemsList.length; i++){
            dbItems.add(new Item(itemsList[i], itemsInfo[i], itemsPrice[i], 0));
        }

    }
    public void toCart(Item item) {
        dbItems.document(item._getId()).update("cartCount",item.getCartCount() + 1)
            .addOnFailureListener(failure -> {
                Toast.makeText(this, "Item " + item.getName() + " couldnt be added to cart", Toast.LENGTH_LONG).show();
            });
        queryData();
    }
    public void addItem() {
        Intent intent = new Intent(this,AddItemActivity.class);
        startActivity(intent);
    }

    public void delete(Item item) {
        DocumentReference reference = dbItems.document(item._getId());

        reference.delete().addOnFailureListener(failure ->{
            Toast.makeText(this, "Item " + item.getName() + " couldnt be deleted.", Toast.LENGTH_LONG).show();
        });

        queryData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.item_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout_button:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.add_item: addItem(); return true;
            case R.id.cart: return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }
}