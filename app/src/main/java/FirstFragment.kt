package com.example.assignmentapplication
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.assignmentapplication.databinding.FragmentFirstBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFirstBinding

    private lateinit var nameEditText: EditText
    private lateinit var linkEditText: EditText
    private lateinit var founderEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var imageEditText: EditText
    private lateinit var intent: Intent



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)
        nameEditText = binding.nameEdittext
        linkEditText = binding.linkEdittext
        founderEditText = binding.founderEdittext
        yearEditText = binding.yearEdittext
        imageEditText = binding.imageTxt

        binding.addButton.setOnClickListener {

            val name = nameEditText.text.toString().trim()
            val link = linkEditText.text.toString().trim()
            val founder = founderEditText.text.toString().trim()
            val year = yearEditText.text.toString().trim()
            val image = imageEditText.text.toString().trim()
            val dbHelper = DatabaseHelper(requireActivity())

            if (name.isNotEmpty() && link.isNotEmpty() && founder.isNotEmpty() && year.isNotEmpty() && image.isNotEmpty()) {

                if (dbHelper.getCount() < 5) {

                    val isInserted = dbHelper.addData(name, link, founder, year, image)

                    if (isInserted) {
                        val manager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

                            var c = NotificationChannel("NN","NN",
                                NotificationManager.IMPORTANCE_DEFAULT)
                            c.description = "...."

                            manager.createNotificationChannel(c)


                        }
                        var noti =  NotificationCompat.Builder(requireActivity(),"NN")
                        noti.setContentTitle("A new site has been added $name")
                        noti.setContentText("The number of websites you use to store it ${5- dbHelper.getCount() }")
                        noti.setOngoing(false)
                        noti.setSmallIcon(R.drawable.ic_notification)
                        noti.build()
                        manager.notify(1,noti.build())
                        Toast.makeText(requireActivity(), "Data inserted successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireActivity(), "Failed to insert data", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val manager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

                        var c = NotificationChannel("NN","NN",
                            NotificationManager.IMPORTANCE_HIGH)
                        c.description = "...."

                        manager.createNotificationChannel(c)


                    }
                    var noti =  NotificationCompat.Builder(requireActivity(),"NN")
                    noti.setContentTitle("لقد وصلت للحد الأقصى للمواقع المسموح بإضافتها")
                    noti.setContentText("لإضافة موقع جديد قم بحذف موقع سابق")
                    noti.setOngoing(false)
                    noti.setSmallIcon(R.drawable.ic_notification)
                    noti.build()
                    manager.notify(1,noti.build())
                }
            }else{
                Toast.makeText(requireActivity(), "Fill the fields", Toast.LENGTH_SHORT).show()


            }





}
        // Inflate the layout for this fragment
        return binding.root

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }




}


