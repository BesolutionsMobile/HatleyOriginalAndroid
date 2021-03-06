package hatelyoriginal.besolutions.com.hatleyoriginal;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import hatelyoriginal.besolutions.com.hatleyoriginal.LocalData.SavedData;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private View mSelectedView;
    Context context;

    public static String sDefSystemLanguage;

    NavigationDrawerAdapter(List<NavigationItem> data, Context context) {
        mData = data;
        this.context = context;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @NonNull
    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.itemView.setClickable(true);

        sDefSystemLanguage = Locale.getDefault().getLanguage();


        //CLICK ON ITEM LOISTNER
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       if (mSelectedView != null) {
                                                           mSelectedView.setSelected(false);
                                                       }
                                                       //mSelectedPosition = viewHolder.getAdapterPosition();
                                                       v.setSelected(true);
                                                       mSelectedView = v;
                                                       if (mNavigationDrawerCallbacks != null)
                                                           mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(viewHolder.getAdapterPosition());
                                                   }
                                               }
        );

        //SET BACKGROUND
        viewHolder.itemView.setBackgroundResource(R.drawable.row_selector);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(mData.get(i).getText());

        if(new SavedData().get_lan(context).equals("ar"))
        {
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null, mData.get(i).getDrawable(), null);

        }else
        {
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(mData.get(i).getDrawable(), null, null, null);

        }

    }


    void selectPosition(int position) {
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_name);
        }
    }
}