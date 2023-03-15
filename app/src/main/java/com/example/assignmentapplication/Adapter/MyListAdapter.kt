

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.assignmentapplication.DatabaseHelper
import com.example.assignmentapplication.R
import com.example.assignmentapplication.com.example.assignmentapplication.Modle.SiteModel

class MyListAdapter(private val context: Context, private val dataSource: ArrayList<SiteModel>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.info, parent, false)

        val web_name = rowView.findViewById(R.id.web_name) as TextView
        val web_founder = rowView.findViewById(R.id.web_founder) as TextView
        val web_year = rowView.findViewById(R.id.web_year) as TextView
        val btn_delete = rowView.findViewById(R.id.btn_delete)  as Button

        val site = getItem(position) as SiteModel

        web_name.text = site.name
        web_founder.text = site.founder
        web_year.text = site.year

        btn_delete.setOnClickListener {
            val dbHelper = DatabaseHelper(context)
            dbHelper.deleteSite(site.id)
            dataSource.remove(site)
            notifyDataSetChanged()
        }

        return rowView
    }
}
