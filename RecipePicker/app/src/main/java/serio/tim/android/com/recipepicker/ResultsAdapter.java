package serio.tim.android.com.recipepicker;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    private String[] data;
    private Context context;
    private HashMap<String, String> h;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ConstraintLayout parentLayout;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.text_view);
            parentLayout = v.findViewById(R.id.parent_layout);
        }
    }

    public ResultsAdapter(Context context, String[] data, HashMap<String, String> h) {
        this.context = context;
        this.data = data;
        this.h = h;
    }

    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(data[position]);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entry = (String) data[position];
                Context context = view.getContext();
                String url = h.get(entry);
                Intent intent = new Intent(context, BrowserActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
