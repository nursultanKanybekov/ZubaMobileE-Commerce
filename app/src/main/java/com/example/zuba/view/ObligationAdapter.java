package com.example.zuba.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zuba.R;
import com.example.zuba.controller.BasketFragment;
import com.example.zuba.model.ObligationSerialiszerModel;
import com.example.zuba.model.ProductsModel;
import com.example.zuba.services.MyDatabaseOperationsServices;

import java.util.List;

public class ObligationAdapter extends RecyclerView.Adapter<ObligationAdapter.ObligationViewHolder> {
    private List<ObligationSerialiszerModel> productsModelList;
    private Context context;
    private Fragment fragment;

    public ObligationAdapter(Context context, List<ObligationSerialiszerModel> productsModelList, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.productsModelList = productsModelList;
    }

    @NonNull
    @Override
    public ObligationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ObligationViewHolder(LayoutInflater.from(context).inflate(R.layout.obligation_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ObligationViewHolder holder, int position) {
        holder.total_price.setText("Общая сумма: "+ productsModelList.get(position).getTotal_price());
        holder.initial_payment.setText("Первоначальная сумма: "+ productsModelList.get(position).getInitial_payment());
        holder.paid.setText("Оплачено: "+ productsModelList.get(position).getPaid());
        holder.reminder.setText("Опаващения: "+ productsModelList.get(position).getReminder());
        holder.price_per_month.setText("Ежемесячная сумма: "+ productsModelList.get(position).getPrice_per_month());
        holder.deadline.setText("Дедлайн: "+ productsModelList.get(position).getDeadline());
        holder.next_date_payment.setText("След. оплата: "+ productsModelList.get(position).getNext_date_payment());
    }

    @Override
    public int getItemCount() {
        return productsModelList.size();
    }


    class ObligationViewHolder extends RecyclerView.ViewHolder {
        private TextView total_price, initial_payment, paid, reminder, price_per_month, deadline, next_date_payment;

        public ObligationViewHolder(@NonNull View view) {
            super(view);
            total_price = view.findViewById(R.id.total_price);
            initial_payment = view.findViewById(R.id.initial_payment);
            paid = view.findViewById(R.id.paid);
            reminder = view.findViewById(R.id.reminder);
            price_per_month = view.findViewById(R.id.price_per_month);
            deadline = view.findViewById(R.id.deadline);
            next_date_payment = view.findViewById(R.id.next_date_payment);
        }
    }
}
