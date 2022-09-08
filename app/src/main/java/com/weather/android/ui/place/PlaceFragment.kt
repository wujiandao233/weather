package com.weather.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.android.MainActivity
import com.weather.android.R

import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment: Fragment() {
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }
//使用lazy函数来获取PlaceViewModel的实力。允许我们在整个类中随时使用viewModel变量
    private lateinit var adapter: PlaceAdapter
//加载fragment_place布局，Fragment的标准用法
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }
    /*给Recycler设置了LayoutManager和适配器，并使用PlaceViewModel中的placeList集合作为数据源
    后调用了EditText的addTextChangedListener方法来监听搜索框内容的变化情况。每当
    搜索框中的内容发生了变化，我们就获取新的内容，然后传递给PlaceViewModel的searchPlaces方法。

    */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter=adapter
        searchPlaceEdit.addTextChangedListener {editable->//监听搜索框内容的变化
            val content = editable.toString()
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)//传递给searchPlaces方法
            }else{
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        /*对PlaceViewModel中的PlaceLiveData对象进行观察，当有任何数据变化时，就会回调到
        传入的Observer接口实现中。然后我们会对回调的数据进行判断，若不为空，那么就添加到
        PlaceViewModel的placeList集合中，并通知PlaceAdapter刷新若为假则打印原因
        */
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer{ result ->
            val places = result.getOrNull()
            if (places!=null){
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })


    }
}