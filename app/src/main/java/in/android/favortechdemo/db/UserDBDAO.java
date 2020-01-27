package in.android.favortechdemo.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import in.android.favortechdemo.entities.UserEntity;

@Dao
public interface UserDBDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(UserEntity... userEntities);


    @Query("SELECT * FROM user WHERE mobile LIKE :mobile")
    public UserEntity getUser(String mobile);

}
