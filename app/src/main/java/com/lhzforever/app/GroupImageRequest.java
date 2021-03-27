package com.lhzforever.app;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.RequestFuture;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GroupImageRequest {
    public static String IMAGE_URL = "http://121.36.36.134:5555/getImage";

    private int[] indices;
    private Response.Listener<Bitmap[]> listener;

    public GroupImageRequest(int[] indices, Response.Listener<Bitmap[]> listener) {
        this.indices = indices;
        this.listener = listener;
    }

    public void start(RequestQueue queue) {
        List<Bitmap> bitmaps = new LinkedList<>();
        for (int index : indices) {
//            RequestFuture<Bitmap> future = RequestFuture.newFuture();
//            ImageRequest imageRequest = new ImageRequest(IMAGE_URL + "?picID=" + index, future, 512, 512, ImageView.ScaleType.CENTER_CROP, null, future);
//            future.setRequest(queue.add(imageRequest));
//
//            try {
//                Bitmap bitmap = future.get();
//            } catch (Exception e) {
//                future.onErrorResponse(new VolleyError(e));
//            }
            ImageRequest imageRequest = new ImageRequest(IMAGE_URL + "?picID=" + index, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    System.out.println(response);
                }
            }, 512, 512, ImageView.ScaleType.CENTER_CROP, null, null);
        }
        this.listener.onResponse(bitmaps.toArray(new Bitmap[0]));
    }
}
