package com.gmail.babyneeds.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.babyneeds.R;
import com.gmail.babyneeds.data.NeedsDatabaseHandler;
import com.gmail.babyneeds.model.Needs;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Needs> needsList;
    private NeedsDatabaseHandler DB;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    public RecyclerViewAdapter(Context context, List<Needs> needsList) {
        this.context = context;
        this.needsList = needsList;
        DB = new NeedsDatabaseHandler(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Needs needs = needsList.get(position);

        holder.showNameTextView.setText(needs.getName());
        holder.showQuantityTextView.setText(String.valueOf(needs.getQuantity()));
        holder.showColorTextView.setText(MessageFormat.format("Color {0}", needs.getColor()));
        holder.showSizeTextView.setText(MessageFormat.format("Size {0}", needs.getSize()));
        holder.showDateTextView.setText(needs.getDate());
    }

    @Override
    public int getItemCount() {
        return needsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView showNameTextView, showQuantityTextView, showColorTextView, showSizeTextView, showDateTextView;
        public Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            showNameTextView = itemView.findViewById(R.id.showNameTextView);
            showQuantityTextView = itemView.findViewById(R.id.showQuantityTextView);
            showColorTextView = itemView.findViewById(R.id.showColorTextView);
            showSizeTextView = itemView.findViewById(R.id.showSizeTextView);
            showDateTextView = itemView.findViewById(R.id.showDateTextView);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId())
            {
                case R.id.editButton:
                    createPopUpDialog(position);
                    break;
                case R.id.deleteButton:
                    confirmationDialog(position);
                    break;
            }
        }
    }

    private void confirmationDialog(final int position)
    {
        builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.confirmation_popup, null);
        Button noButton = view.findViewById(R.id.confirmationNoButton);
        Button yesButton = view.findViewById(R.id.confirmationYesButton);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.deleteNeeds(needsList.get(position).getId());
                needsList.remove(position);
                notifyItemRemoved(position);
                dialog.cancel();
            }
        });
    }

    private void createPopUpDialog(final int position) {
        builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popup, null);

        Button addButton = view.findViewById(R.id.addButton);
        final TextView nameAddEditText = view.findViewById(R.id.nameAddEditText);
        final TextView quantityAddEditText = view.findViewById(R.id.quantityAddEditText);
        final TextView colorAddEditText = view.findViewById(R.id.colorAddEditText);
        final TextView sizeAddEditText = view.findViewById(R.id.sizeAddEditText);

        Needs needs = needsList.get(position);
        final int id = needs.getId();

        addButton.setText("UPDATE");
        nameAddEditText.setText(needs.getName());
        quantityAddEditText.setText(String.valueOf(needs.getQuantity()));
        colorAddEditText.setText(needs.getColor());
        sizeAddEditText.setText(String.valueOf(needs.getSize()));

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean canSave = true;
                if (nameAddEditText.getText().toString().isEmpty()) {
                    nameAddEditText.setHintTextColor(context.getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if (quantityAddEditText.getText().toString().isEmpty()) {
                    quantityAddEditText.setHintTextColor(context.getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if (colorAddEditText.getText().toString().isEmpty()) {
                    colorAddEditText.setHintTextColor(context.getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if (sizeAddEditText.getText().toString().isEmpty()) {
                    sizeAddEditText.setHintTextColor(context.getResources().getColor(R.color.mediumRed));
                    canSave = false;
                }
                if (canSave) {
                    Needs updatedNeeds = new Needs(id, nameAddEditText.getText().toString().trim(), Integer.parseInt(quantityAddEditText.getText().toString()), colorAddEditText.getText().toString().trim(), Integer.parseInt(sizeAddEditText.getText().toString()));
                    DB.updateNeeds(updatedNeeds);
                    needsList.set(position, updatedNeeds);
                    notifyItemChanged(position);
                    dialog.dismiss();
                }
            }
        });
    }


}



















