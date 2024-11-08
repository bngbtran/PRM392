package com.example.tripbuddy.AppDatabase;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.tripbuddy.DAO.BookMarkDAO;
import com.example.tripbuddy.DAO.DestinationDAO;
import com.example.tripbuddy.DAO.HistoryDAO;
import com.example.tripbuddy.DAO.ReviewDAO;
import com.example.tripbuddy.DAO.ScheduleDAO;
import com.example.tripbuddy.DAO.UserDAO;
import com.example.tripbuddy.Models.BookMarkDestination;
import com.example.tripbuddy.Models.Destination;
import com.example.tripbuddy.Models.History;
import com.example.tripbuddy.Models.LocalDateConverter;
import com.example.tripbuddy.Models.LocalTimeConverter;
import com.example.tripbuddy.Models.Review;
import com.example.tripbuddy.Models.Schedule;
import com.example.tripbuddy.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Destination.class, Review.class, BookMarkDestination.class, Schedule.class, History.class}, version = 1, exportSchema = false)
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
    public abstract DestinationDAO destinationDAO();
    public abstract ReviewDAO reviewDAO();
    public abstract BookMarkDAO bookMarkDao();
    public abstract ScheduleDAO scheduleDAO();
    public abstract HistoryDAO historyDAO();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_tripBuddy_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Insert sample data on database creation
            databaseWriteExecutor.execute(() -> {
                DestinationDAO destinationDao = INSTANCE.destinationDAO();
                UserDAO userDao = INSTANCE.userDao();

                // Insert sample users
                User user = new User("user","admin@travel.com","123456");
                userDao.insert(user);

                destinationDao.insert(new Destination("Hoi An Ancient Town", "Quang Nam", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Ph%E1%BB%91_c%E1%BB%95_H%E1%BB%99i_An_-_NKS.jpg/402px-Ph%E1%BB%91_c%E1%BB%95_H%E1%BB%99i_An_-_NKS.jpg", "A charming ancient town known for its historic architecture and vibrant lantern-lit streets.", 108.324805, 15.880058));
                destinationDao.insert(new Destination("Phong Nha-Ke Bang National Park", "Quang Binh", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Phongnhakebang6.jpg/426px-Phongnhakebang6.jpg", "UNESCO World Heritage site with impressive limestone karst landscapes and caves.", 106.148847, 17.553437));
                destinationDao.insert(new Destination("Hue Imperial City", "Thua Thien-Hue", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Ng%E1%BB%8D_M%C3%B4n_Hu%E1%BA%BF_-_NKS.jpg/402px-Ng%E1%BB%8C_M%C3%B4n_Hu%E1%BA%BF_-_NKS.jpg", "A grand imperial city that served as Vietnam’s capital during the Nguyen dynasty.", 107.579719, 16.463713));
                destinationDao.insert(new Destination("Ha Long Bay", "Quang Ninh", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Vietnam-2145504_1920.jpg/215px-Vietnam-2145504_1920.jpg", "Famous for its emerald waters and thousands of limestone islands topped with rainforests.", 107.048456, 20.910051));
                destinationDao.insert(new Destination("My Son Sanctuary", "Quang Nam", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/My_Son.jpg/360px-My_Son.jpg", "An ancient Hindu temple complex with magnificent ruins and unique Champa architecture.", 108.112783, 15.774025));
                destinationDao.insert(new Destination("Sapa", "Lao Cai", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Th%E1%BB%8B_tr%E1%BA%A5n_Sa_Pa.jpg/402px-Th%E1%BB%8B_tr%E1%BA%A5n_Sa_Pa.jpg", "Renowned for terraced fields, mountainous scenery, and ethnic minority culture.", 103.844813, 22.336112));
                destinationDao.insert(new Destination("Hanoi Old Quarter", "Hanoi", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Old_Quarter_Street_Scene_-_Hanoi_-_Vietnam_%2848256301206%29.jpg/330px-Old_Quarter_Street_Scene_-_Hanoi_-_Vietnam_%2848256301206%29.jpg", "Bustling area with narrow streets showcasing traditional Vietnamese architecture.", 105.853260, 21.035555));
                destinationDao.insert(new Destination("Cu Chi Tunnels", "Ho Chi Minh City", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/VietnamCuChiTunnels.jpg/450px-VietnamCuChiTunnels.jpg", "A historic underground network used during the Vietnam War.", 106.466591, 10.976671));
                destinationDao.insert(new Destination("Mekong Delta", "Southwestern Vietnam", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Tractor_Mekong_Delta_Vietnam.jpg/375px-Tractor_Mekong_Delta_Vietnam.jpg", "Known as Vietnam's 'rice bowl,' featuring floating markets and lush landscapes.", 105.780884, 10.241487));
                destinationDao.insert(new Destination("Ba Na Hills", "Da Nang", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Panoramic_View.jpg/525px-Panoramic_View.jpg", "A scenic hill station with stunning views, French architecture, and the Golden Bridge.", 108.007291, 15.997193));
                destinationDao.insert(new Destination("Nha Trang Beach", "Khanh Hoa", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Nha_Trang%2C_Kh%C3%A1nh_H%C3%B2a.png/255px-Nha_Trang%2C_Kh%C3%A1nh_H%C3%B2a.png", "A popular coastal city known for beautiful beaches and water activities.", 109.196749, 12.238791));
                destinationDao.insert(new Destination("Phu Quoc Island", "Kien Giang", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/H%C3%B2n_Th%C6%A1m_cable_car_above_the_An_Th%E1%BB%9Di_township.jpg/210px-H%C3%B2n_Th%C6%A1m_cable_car_above_the_An_Th%E1%BB%9Di_township.jpg", "Tropical paradise known for pristine beaches and rich marine life.", 104.010967, 10.227029));
                destinationDao.insert(new Destination("Tam Coc - Bich Dong", "Ninh Binh", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Muaxuantamcoc.jpg/375px-Muaxuantamcoc.jpg", "Dubbed 'Ha Long Bay on land' with serene rivers and limestone mountains.", 105.924747, 20.202873));
                destinationDao.insert(new Destination("Con Dao", "Ba Ria - Vung Tau", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/C%C3%B4n_%C4%90%E1%BA%A3o%2C_B%C3%A0_R%E1%BB%8Ba_-_V%C5%A9ng_T%C3%A0u%2C_Vietnam_-_panoramio_%2833%29.jpg/420px-C%C3%B4n_%C4%90%E1%BA%A3o%2C_B%C3%A0_R%E1%BB%8Ba_-_V%C5%A8ng_T%C3%A0u%2C_Vietnam_-_panoramio_%2833%29.jpg", "Secluded island group known for stunning beaches and historical sites.", 106.627046, 8.684124));
                destinationDao.insert(new Destination("Da Lat", "Lam Dong", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Xuan_Huong_Lake_11.jpg/213px-Xuan_Huong_Lake_11.jpg", "Nicknamed the 'City of Eternal Spring,' known for flowers and cool climate.", 108.438277, 11.940419));
                destinationDao.insert(new Destination("Mui Ne", "Binh Thuan", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/M%C5%A9i_N%C3%A9_Fishing_Village.jpg/420px-M%C5%A9i_N%C3%A9_Fishing_Village.jpg", "Coastal town famed for its sand dunes and kite surfing opportunities.", 108.336131, 10.946447));
                destinationDao.insert(new Destination("Cat Ba Island", "Hai Phong", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/%C4%90%E1%BA%A3oKh%E1%BB%89_C%C3%A1tB%C3%A0_VN.jpg/450px-%C4%90%E1%BA%A3oKh%E1%BB%89_C%C3%A1tB%C3%A0_VN.jpg", "Largest island in Ha Long Bay, known for its national park and adventure activities.", 107.048292, 20.727104));
                destinationDao.insert(new Destination("Trang An Scenic Landscape Complex", "Ninh Binh", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Muaxuantamcoc.jpg/390px-Muaxuantamcoc.jpg", "UNESCO site with majestic limestone landscapes and ancient temples.", 105.937387, 20.280953));
                destinationDao.insert(new Destination("Bach Ma National Park", "Thua Thien-Hue", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Bach_Ma_NP2.jpg/426px-Bach_Ma_NP2.jpg", "Biodiversity hotspot with trails, waterfalls, and a rich variety of flora and fauna.", 107.835367, 16.192209));
                destinationDao.insert(new Destination("Son Doong Cave", "Quang Binh", "https://upload.wikimedia.org/wikipedia/commons/8/85/Son_Doong_cave_%28Quang_Binh%2C_Vietnam%29.jpg", "World's largest natural cave with awe-inspiring underground landscapes.", 106.287135, 17.458987));

            });
        }
    };

}


