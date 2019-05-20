package com.example.regroup.Events;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regroup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EventManagementActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    final StorageReference storageRef = storage.getReferenceFromUrl("gs://regroup-7c1c2.appspot.com/");

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference eventsRef = db.collection("events");

    List<String> names;
    List<Bitmap> images;
    List<String> eventId;
    List<String> date;
    ListView listView;
    ListViewAdapter adapter = new ListViewAdapter();

    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        adapter.deleteItem(index);
//        Log.i("TEST", "onContextItemSelected: " +adapter.getItem(index).toString() + "  index:  " + index);
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);


        names = new ArrayList<>();
        date = new ArrayList<>();
        images = new ArrayList<>();
        eventId = new ArrayList<>();


        eventsRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (final DocumentSnapshot snapshot : task.getResult()) {
                                if(snapshot.get("owner").toString()!= null){
                                    if(snapshot.get("owner").toString().equals(currentUser)){
                                        names.add(snapshot.toObject(cards.class).getName());
                                        eventId.add(snapshot.toObject(cards.class).getId());
                                        date.add(snapshot.toObject(cards.class).getDate());
//                                        StorageReference imagesRef = storageRef.child("Events_Images/" + snapshot.toObject(cards.class).getImage());
//                                        imagesRef.getBytes(Long.MAX_VALUE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<byte[]> task) {
//                                                if(task.isComplete()){
//                                                    Bitmap bitmap = BitmapFactory.decodeByteArray(task.getResult(),0,task.getResult().length);
//                                                    Log.i("BITMAP", "onComplete: " + bitmap.toString());
//                                                    images.add(bitmap);
//                                                }
//                                            }
//                                        });
                                    }
                                }

                            }
                        }
                        listView = findViewById(R.id.eventMgmtListView);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                openParticipantsActivity(eventId.toArray()[position].toString());
                            }
                        });
                        registerForContextMenu(listView);
                    }
                });
    }

    class ListViewAdapter extends BaseAdapter{

        public void deleteItem (int position) {
            DeleteItem(names.toArray()[position].toString() + date.toArray()[position].toString());
            names.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return names.toArray()[position].toString() + date.toArray()[position].toString();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_listview_layout, null);

            ImageView imageView = convertView.findViewById(R.id.imageView_list);
            TextView textView = convertView.findViewById(R.id.textView_list);
            //imageView nenori veikti :(
            //imageView.setImageBitmap((Bitmap) images.toArray()[position]);
            textView.setText(names.toArray()[position].toString());

            return convertView;
        }
    }

    public void DeleteItem(final String itemId){
        eventsRef.document(itemId).delete();
    }

    public void openParticipantsActivity(String eventId){
        Intent intent = new Intent(this, ChooseParticipantsActivity.class);
        intent.putExtra("eventId", eventId);

        startActivity(intent);
    }
}
