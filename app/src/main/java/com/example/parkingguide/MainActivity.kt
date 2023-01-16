package com.example.parkingguide

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.parkingguide.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mQueue: RequestQueue

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            val url = "https://api.thingspeak.com/channels/1905081/fields/1.json?api_key=94NIXW23YSREHW6Y&results=2"
            mQueue = Volley.newRequestQueue(this)

            val request = JsonObjectRequest(
                Request.Method.GET, url, null, {
                    val jsonArray: JSONArray = it.getJSONArray("feeds")
                    for(i in 0..jsonArray.length()) {
                        if (i == 1) {
                            val feed: JSONObject = jsonArray.getJSONObject(i)

                            val field1: Int = feed.getInt("field1")

                            binding.tv1.append(field1.toString())

                            if (field1 in 0..100) {
                                binding.tv1.setText("Jarak = " + field1 +"cm")
                                binding.tv2.setText("Kondisi = Awas!!")
                                binding.tv3.setText("Buzzer = Menyala")
                                binding.tv4.setText("Lampu LED = Menyala")
                                binding.tv5.setText("BERHENTIKAN MOBIL!!")
                            } else {
                                binding.tv1.setText("Jarak = " + field1 +"cm")
                                binding.tv2.setText("Kondisi = Aman")
                                binding.tv3.setText("Buzzer = Mati")
                                binding.tv4.setText("Lampu LED = Mati")
                                binding.tv5.setText("")
                            }
                        }
                    }
                }, {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                })
            mQueue.add(request)
        }

    }
}