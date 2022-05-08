package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lakasdekorshop.ItemListActivity;
import com.example.lakasdekorshop.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Filterable {
    private ArrayList<Item> items;
    private ArrayList<Item> itemsAll;
    private Context context;
    private int lastPos = -1;


    public ItemAdapter(Context context, ArrayList<Item> items){
        this.items = items;
        this.itemsAll = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        Item currentItem = items.get(position);

        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Item> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.count = itemsAll.size();
                results.values = itemsAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Item item : itemsAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            items = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView desc;
        private TextView cost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemtitle);
            desc = itemView.findViewById(R.id.itemsubtitle);
            cost = itemView.findViewById(R.id.itemcost);


        }

        public void bindTo(Item item){
            title.setText(item.getName());
            desc.setText(item.getDesc());
            cost.setText(item.getCost());
            itemView.findViewById(R.id.toCart).setOnClickListener(view -> ((ItemListActivity)context).toCart(item));
            itemView.findViewById(R.id.delete).setOnClickListener(view -> ((ItemListActivity)context).delete(item));
        }
    }
}
