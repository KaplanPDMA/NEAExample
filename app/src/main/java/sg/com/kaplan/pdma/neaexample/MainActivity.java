package sg.com.kaplan.pdma.neaexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends ActionBarActivity {

    TextView textView;
    TextView textViewTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);

        //please download your API keys from https://www.nea.gov.sg/api
        String dataset = "nowcast";
        //String dataset = "12hrs_forecast";
        String keyref = "781CF461BB6606ADEA6B1B4F3228DE9D586412BA7716CB8F";

        String url = "http://www.nea.gov.sg/api/WebAPI?dataset=" + dataset + "&keyref=" + keyref;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
//                textView.setText(new String(response));

                String line ="";

                XmlPullParserFactory factory = null;
                try {
                    factory = XmlPullParserFactory.newInstance();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                factory.setNamespaceAware(true);
                XmlPullParser xpp = null;
                try {
                    xpp = factory.newPullParser();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                try {
                    xpp.setInput( new StringReader( new String(response) ) );
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                int event = 0;
                try {
                    event = xpp.getEventType();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                while (event != XmlPullParser.END_DOCUMENT)
                {
                    String name= xpp.getName();
                    switch (event){
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.END_TAG:

                            if(name.equals("area")){
                                String area = xpp.getAttributeValue(null,"name");
                                String forecast = xpp.getAttributeValue(null,"forecast");
                                line += area + ": " + forecast + "\n";
                                textView.setText(line);
                            }

                            //<temperature unit='Degrees Celsius' low='27 ' high=' 32' />
//                            if(name.equals("temperature")){
//                                String unit = xpp.getAttributeValue(null,"unit");
//                                String low = xpp.getAttributeValue(null,"low");
//                                String high = xpp.getAttributeValue(null,"high");
//                                String output = "" + low + " - " + high + " " + unit;
//                                textViewTemperature.setText(output);
//                            }



                            break;
                    }
                    try {
                        event = xpp.next();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
