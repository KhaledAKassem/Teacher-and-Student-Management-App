package medic.esy.es;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by master on 15/03/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements View.OnClickListener {

    ArrayList<Model> list;
    LayoutInflater mInflater;
    Context context;

    public RecyclerAdapter(Context c, ArrayList<Model> list){

        this.list = list;
        context = c;
        mInflater = LayoutInflater.from(c);

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Model item = list.get(position);
        holder.setData(item,position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                action to go next activity

//                Intent i = new Intent(context,ActivityName.class);
//                i.putExtra("name",item.getcName());
//                context.startActivity(i);
//
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        int position;
        Model current;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.sahaba);
            cardView = (CardView) itemView.findViewById(R.id.next);
        }

        public void setData(Model item, int position) {
            this.text.setText(item.getcName());
            this.position = position;
            current = item;

        }

    }
}
