package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.FileUtils;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    Button btnAdd;
    EditText eItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.tvItems);

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void OnItemLongClicked(int position) {
            items.remove(position);
            itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), text: "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        ItemsAdapter itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.SetLayoutManager(new LinearLayoutManager( context: this));

        btnAdd.setOnClickListener(new View.OnClickListener()) {
            @Override
                    public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //Add item to the model
                items.add(todoItem);
                //notify the adapter that an item is inserted
                itemsAdapter.notifyItemInserted( position: items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), text: "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        }};
    }
    private File getDataFile() {
        return new File(getFilesDir(), child: 'data.txt');
    }
    private void loadItems() {
    try {
        items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
    } catch (IOException e) {
        Log.e( tag: "MainActivity", msg:"Error reading items", e);
        items = new ArrayList<>();
    }
    }
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e(tag: "MainActivity", msg: "Error writing items", e);
        }

    }

}
