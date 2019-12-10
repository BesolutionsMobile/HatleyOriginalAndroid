package hatelyoriginal.besolutions.com.hatleyoriginal.Utils;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class utils_adapter {

    /**
     *GRIDVIEW Adapter
     */
    public void griddAdapters(RecyclerView recyclerView, RecyclerView.Adapter dd, Context context, int num)
    {
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(context,num);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter= dd;
        recyclerView.setAdapter(adapter);
    }

    /**
     *List Adapter
     */
    public void Adapter(RecyclerView recyclerView, RecyclerView.Adapter dd, Context context)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dd);
    }

    /**
     *Horozintal Adapter
     */
    public void Horozintal(RecyclerView recyclerView, RecyclerView.Adapter dd, Context context)
    {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter= dd;
        recyclerView.setAdapter(adapter);
    }
}
