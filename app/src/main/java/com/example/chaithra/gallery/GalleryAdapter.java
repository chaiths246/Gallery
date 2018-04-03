package com.example.chaithra.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by chaithra on 3/26/18.
 */

public class GalleryAdapter extends ArrayAdapter<Gallery> {
    public GalleryAdapter(Context context, int resource) {

        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Gallery item = getItem(position);
        ViewHolder holder;
        if (v == null) {
            holder = new ViewHolder();
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_adapter, parent, false);
            holder.image = (ImageView) v.findViewById(R.id.imageview);
            holder.title = (TextView) v.findViewById(R.id.title);
            v.setTag(holder);

        } else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) v.getTag();
        }
        holder.image.setImageResource(R.drawable.ic_launcher_background);
        new DownloadImageTask(holder.image).execute(item.getImage());
        holder.title.setText(item.getTitile());

        return v;
    }

    private class ViewHolder {
        ImageView image;
        TextView title;


    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}


