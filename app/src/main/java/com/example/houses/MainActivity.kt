package com.example.houses

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.last

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //firebase database setup
        val database = Firebase.database
        val dbRef = database.getReference("Houses")



        // getting the recyclerview by its id
        val recycl :RecyclerView = findViewById(R.id.recyclerView)

        // this creates a vertical layout Manager
        recycl.layoutManager = LinearLayoutManager(this)

        // ArrayList of class HouseModel
        val data = ArrayList<HouseModel>()

        // adding data to ArrayList
      //  data.add(HouseModel(2,"PTA West"))
       // data.add(HouseModel(5,"PTA North"))
        //data.add(HouseModel(7,"PTA Central"))

        // This will pass the ArrayList to our Adapter
        //var custom_adater = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        //var custom_adater = CustomAdapter(data)
        //recycl.adapter = custom_adater

        val input_location = findViewById<EditText>(R.id.input_location)
        val input_rooms = findViewById<EditText>(R.id.input_room_numbers)
        var roomNum :Int

        val btn_save = findViewById<Button>(R.id.button)
        var temp_house :HouseModel
        lateinit var lastChildKey :String

        btn_save.setOnClickListener{
            roomNum = input_rooms.text.toString().toInt()
            temp_house = HouseModel(roomNum,input_location.text.toString())

            var temp_key = lastChildKey.toInt() +1

            var temp_map = mapOf("No of rooms" to roomNum, "Location" to input_location.text.toString())
            dbRef.child(temp_key.toString()).setValue(temp_map)




        }

        dbRef.addValueEventListener(
            object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (!snapshot.hasChildren()) {
                        dbRef.setValue(listOf(mapOf("test" to "test")))
                    }

                    lastChildKey = snapshot.children.last().key.toString()

                    var custom_adapter = CustomAdapter(snapshot.children.toList())
                    recycl.adapter = custom_adapter


                }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

            })









    }

}
