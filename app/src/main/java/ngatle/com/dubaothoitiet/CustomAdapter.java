package ngatle.com.dubaothoitiet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
//public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
//    Context context;
//    ArrayList<ThoiTiet> arrayList;
//
//    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//    }
//
//
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        View item= inflater.inflate(R.layout.list1,null);
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        viewHolder. tvngay.setText(thoitiet.Day);
//        tvtrangthai.setText(thoitiet.Status);
//        tvmax.setText(thoitiet.Max+"°C");
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        TextView tvngay;
//        TextView tvtrangthai;
//        TextView tvmax;
//        ImageView anh;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
////            ThoiTiet thoitiet = arrayList.get(i);
////
//        TextView tvngay = (TextView) itemView.findViewById(R.id.Tvntm3);
//        TextView tvtrangthai = (TextView) itemView.findViewById(R.id.Tvttm3);
//        TextView tvmax = (TextView) itemView.findViewById(R.id.maxm3);
//        TextView tvmin = (TextView) itemView.findViewById(R.id.maxm3);
//        ImageView anh = (ImageView) itemView.findViewById(R.id.Imm3);
//
//        }
//    }
//}
public class CustomAdapter extends BaseAdapter{

    Context context;
    ArrayList<ThoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view= inflater.inflate(R.layout.list1,null);

        ThoiTiet thoitiet = arrayList.get(i);

        TextView tvngay = (TextView) view.findViewById(R.id.Tvntm3);
        TextView tvtrangthai = (TextView) view.findViewById(R.id.Tvttm3);
        TextView tvmax = (TextView) view.findViewById(R.id.maxm3);
        TextView tvmin = (TextView) view.findViewById(R.id.maxm3);
        ImageView anh = (ImageView) view.findViewById(R.id.Imm3);

        tvngay.setText(thoitiet.Day);
        tvtrangthai.setText(thoitiet.Status);
        tvmax.setText(thoitiet.Max+"°C");


        Picasso.with(context).load("http://openweathermap.org/img/w/"+ thoitiet.Img+".png").into(anh);


        return view;
    }
}