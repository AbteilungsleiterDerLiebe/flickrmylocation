package de.hs_bremen.nhinte.flickrmylocation;

/**
 * Created by Niklas on 10.03.2016.
 */
public class FlickrApiQueryManager {

    // String to create Flickr API urls
    private static  final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?method=";
    private static  final String FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search";
    private static  final String FLICKR_GET_SIZES_STRING = "flickr.photos.getSizes";
    private static  final int FLICKR_PHOTOS_SEARCH_ID = 1;
    private static  final int FLICKR_GET_SIZES_ID = 2;
    private static  final int NUMBER_OF_PHOTOS = 20;
    private static  final String TAGS_STRING = "&tags=City%2CLandscape%2CNature";
    private static  final String PHOTO_ID_STRING = "&photo_id=";
    private static  final String FORMAT_STRING = "&format=json";
    public  static  final String GEO_CONTEXT_STRING = "&geo_context=0";
    public  static  final int PHOTO_THUMB = 111;
    public  static  final int PHOTO_LARGE = 222;
   // public  static  final String LAT    = "&lat=53.0833333";
   // public  static  final String LON    = "&lon=8.8";
    //public          final double LAT    = 53.0833333;
    //public          static  final double LON    = 8.8;
    public  static  final String RADIUS = "&10";
    public  static  final String FORMAT = "&format=json";


    //API Key
    private static final String APIKEY_SEARCH_STRING = "&api_key=e07a1743a12566b5e20dd17eae2a295e";

    // creating the URL
    public String createURL(double LAT, double LON) {
        String url = null;
        String method_type = "";
        method_type = FLICKR_PHOTOS_SEARCH_STRING;
        url = FLICKR_BASE_URL + method_type + APIKEY_SEARCH_STRING + TAGS_STRING + GEO_CONTEXT_STRING + "&lat=" + LAT + "&lon=" + LON + RADIUS + FORMAT + "&per_page=10&page=1&nojsoncallback=1";
        return url;
    }
}
