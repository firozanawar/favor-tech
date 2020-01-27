package in.android.favortechdemo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import in.android.favortechdemo.entities.ImageEntity;
import io.reactivex.Flowable;

@Dao
public interface ImageDBDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ImageEntity... imageEntities);

    @Update
    public void update(ImageEntity... imageEntities);


    @Delete
    public void delete(ImageEntity... imageEntities);

    @Query("SELECT * FROM imagecache")
    public Flowable<List<ImageEntity>> getAllImageUrl();

    @Query("SELECT * FROM imagecache WHERE url LIKE :url")
    public ImageEntity getImageUrl(String url);

}
