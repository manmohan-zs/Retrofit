package com.example.networkwithretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retService: AlbumService =
            RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val listOfAlbums = retService.getAlbums()
            emit(listOfAlbums)
        }
        responseLiveData.observe(this, Observer {
            val albumsList: MutableListIterator<AlbumsItem>? = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem: AlbumsItem = albumsList.next()
                    Log.i("MYTAG", albumsItem.title)
                }
            }
        })

        val responseLiveDataForQuery: LiveData<Response<Albums>> = liveData {
            val listOfAlbums = retService.getSortedAlbums(3)
            emit(listOfAlbums)
        }
        //QueryResponse
        responseLiveDataForQuery.observe(this, Observer {
            val albumsList: MutableListIterator<AlbumsItem>? = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem: AlbumsItem = albumsList.next()
                    Log.i("MY_TAG_Query", albumsItem.title)
                }
            }
        })


        val responseLiveDataForPath: LiveData<Response<AlbumsItem>> = liveData {
            val albumItem = retService.getAlbum(3)
            emit(albumItem)
        }
        responseLiveDataForPath.observe(this, Observer {
            val albumItem = it.body()
            albumItem?.let { it1 ->
                Log.i("MY_TAG_PATH", albumItem.title)
            }
        })
        uploadAlbum(retService)
    }

    private fun uploadAlbum(retService: AlbumService) {
        val album = AlbumsItem(0, "My title", 3)
        val postResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }
        postResponse.observe(this, Observer {

        })
    }
}