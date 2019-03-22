package edu.stts.mdpweek5;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private static RVClickListener mylistener;

    private ArrayList<Order> orderlist;
    public MenuAdapter(ArrayList<Order> arrOrder, RVClickListener rvcl)
    {
        this.orderlist = arrOrder;
        mylistener = rvcl;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.rowitem, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvType.setText(orderlist.get(i).getQty() + " " + orderlist.get(i).getType());
        viewHolder.tvSubtotal.setText("Rp. " + orderlist.get(i).getSubtotal());
        String toppings = "with Toppings : ";
        if(orderlist.get(i).getToppings() != null) {
            for (int j = 0; j < orderlist.get(i).getToppings().size()-1; j++) {
                toppings += orderlist.get(i).getToppings().get(j) + ", ";
            }
            int j = orderlist.get(i).getToppings().size()-1;
            if(j>=0)
                toppings += orderlist.get(i).getToppings().get(j);
        }
        viewHolder.tvToppings.setText(toppings);
    }

    @Override
    public int getItemCount() {
        return (orderlist!=null)?orderlist.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvType,tvToppings,tvSubtotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType =(TextView)itemView.findViewById(R.id.textView_qty_type);
            tvToppings = (TextView)itemView.findViewById(R.id.textView_toppings);
            tvSubtotal = (TextView)itemView.findViewById(R.id.textView_subtotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mylistener.recyclerViewListClicked(v,ViewHolder.this.getLayoutPosition());
                }
            });
        }
    }
}
