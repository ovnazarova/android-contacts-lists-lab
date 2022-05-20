package ru.yandex.practicum.contacts.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.yandex.practicum.contacts.R;
import ru.yandex.practicum.contacts.databinding.ItemContactBinding;
import ru.yandex.practicum.contacts.ui.model.ContactUi;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final AsyncListDiffer<ContactUi> differ = new AsyncListDiffer<>(
            new AdapterListUpdateCallback(this),
            new AsyncDifferConfig.Builder<>(new ListDiffCallback()).build()
    );

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemContactBinding binding = ItemContactBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setItems(List<ContactUi> items) {
        differ.submitList(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemContactBinding binding;

        public ViewHolder(@NonNull ItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> { });
        }

        public void bind(ContactUi contact) {
            binding.name.setText(contact.getName());
            Glide.with(binding.contactPhoto)
                    .load(contact.getPhoto())
                    .circleCrop()
                    .placeholder(R.drawable.ic_avatar)
                    .fallback(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(binding.contactPhoto);

            final int phoneVisibility = TextUtils.isEmpty(contact.getPhone()) ? View.GONE : View.VISIBLE;
            binding.phone.setText(contact.getPhone());
            binding.phone.setVisibility(phoneVisibility);

            binding.contactType.setData(contact.getTypes());
        }
    }

    static class ListDiffCallback extends DiffUtil.ItemCallback<ContactUi> {

        @Override
        public boolean areItemsTheSame(@NonNull ContactUi oldItem, @NonNull ContactUi newItem) {
            return oldItem.hashCode() == newItem.hashCode();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ContactUi oldItem, @NonNull ContactUi newItem) {
            return oldItem.equals(newItem);
        }
    }


}
