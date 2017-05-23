package medic.esy.es;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by master on 21/05/2017.
 */
class RecyclerViewAdapterForStudent extends RecyclerView.Adapter<RecyclerViewAdapterForStudent.ViewHolderStudent> {

    private ArrayList<String> values;
    Context context;

    RecyclerViewAdapterForStudent(ArrayList<String> values, Context context) {
        this.values = values;
        this.context = context;
    }



    @Override
    public ViewHolderStudent onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderStudent(LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_row,parent,false));
    }



    @Override
    public void onBindViewHolder(ViewHolderStudent holder, final int position) {
        holder.name.setText(values.get(position));
        holder.courseDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context,StartTask.class);
                String pos=values.get(position);
                i.putExtra("name2",pos);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolderStudent extends RecyclerView.ViewHolder {
        private TextView name;
        CardView courseDetails;
        ViewHolderStudent(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_item_text);
            courseDetails = (CardView) itemView.findViewById(R.id.courseDetails);
        }
    }
}
