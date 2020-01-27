package in.android.favortechdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import in.android.favortechdemo.R;
import in.android.favortechdemo.databinding.MainfeedRowBinding;
import in.android.favortechdemo.entities.ImageEntity;
import in.android.favortechdemo.utils.Logs;
import in.android.favortechdemo.viewmodels.MainFeedFragmentViewModel;
import retrofit2.Retrofit;

public class MainFeedAdapter extends RecyclerView.Adapter<MainFeedAdapter.ViewHolder> {

    public Retrofit retrofit;
    private List<ImageEntity> mValues;
    private OnListFragmentInteractionListener mListener;
    private MainFeedFragmentViewModel mViewModel;
    private Context context;

    public MainFeedAdapter(Context context, @NotNull List<ImageEntity> values, @Nullable OnListFragmentInteractionListener mListener, MainFeedFragmentViewModel mViewModel, Retrofit retrofit) {
        this.mValues = values;
        this.retrofit = retrofit;
        this.mListener = mListener;
        this.mViewModel = mViewModel;
        this.context = context;
    }

    @NotNull
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainfeed_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {

        ImageEntity dataModel = mValues.get(position);
        holder.setViewModel(dataModel);
        getImage(holder.binding.teacherImageIv, dataModel.url, position);
        holder.itemView.setOnClickListener(v -> mListener.onListFragmentInteraction(dataModel));
    }


    public int getItemCount() {
        return this.mValues.size();
    }

    @Override
    public void onViewAttachedToWindow(@NotNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NotNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    private void getImage(ImageView imageView, String url, int pos) {
        mViewModel.downloadImage(retrofit, url, pos).observe((LifecycleOwner) context, imageEntity -> {
            Logs.v("INZY", imageEntity.picName);

            imageView.setImageBitmap(loadBitmap(context, imageEntity.picName));
        });
    }

    private Bitmap loadBitmap(Context context, String picName) {
        Bitmap b = null;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(picName);
            b = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(@Nullable ImageEntity imageEntity);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MainfeedRowBinding binding;


        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bind();
        }

        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(ImageEntity viewModel) {
            if (binding != null) {
                binding.setData(viewModel);
            }
        }
    }
}
