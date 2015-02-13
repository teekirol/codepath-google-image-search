package codepath.com.googleimagesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import codepath.com.googleimagesearch.R;
import codepath.com.googleimagesearch.models.SearchResult;

public class SearchResultAdapter extends ArrayAdapter<SearchResult> {

    private class ViewHolder {
        ImageView thumb;
        TextView title;
    }

    public SearchResultAdapter(Context context, ArrayList<SearchResult> images) {
        super(context, 0, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResult sr = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.thumb = (ImageView) convertView.findViewById(R.id.thumb);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(getContext()).load(sr.getThumbUrl()).into(viewHolder.thumb);
        viewHolder.title.setText(sr.getTitle());

        return convertView;
    }
}
