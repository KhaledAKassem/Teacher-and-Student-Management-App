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
 * Created by master on 21/05/2017.
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> values;
    Context context;

    RecyclerViewAdapter(ArrayList<String> values, Context context) {
        this.values = values;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_row,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(values.get(position));
        holder.courseDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context,AddTask.class);
                String pos=values.get(position);
                i.putExtra("name",pos);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        CardView courseDetails;
        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_item_text);
            courseDetails = (CardView) itemView.findViewById(R.id.courseDetails);
        }
    }
}
