package org.ecaib.countries;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {
    @Query("select * from Country")
    LiveData<List<Country>> getCountries();

    @Insert
    void addCountries(List<Country> countries);

    @Delete
    void deleteCountry(Country country);

    @Query("DELETE FROM Country")
    void deleteCountries();
}
