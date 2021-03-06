package com.adri.takenotes.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.adri.takenotes.R
import com.adri.takenotes.model.AllergensEnums
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.InjectViews

import java.lang.ref.WeakReference

class DishView extends CardView {

    @InjectView(R.id.image_dish)
    ImageView imageDish

    @InjectView(R.id.title_dish)
    TextView titleDish

    @InjectView(R.id.dish_price)
    TextView priceDish

    @InjectView(R.id.description_dish)
    TextView descriptionDish

    @InjectView(R.id.text_quantity)
    TextView quantityText

    @InjectView(R.id.quantity_dishes)
    TextView quantityDishes

    @InjectViews([R.id.allergens_gluten, R.id.allergens_crustaceos, R.id.allergens_huevos, R.id.allergens_pescado,
            R.id.allergens_cacahuetes, R.id.allergens_soja, R.id.allergens_lacteos, R.id.allergens_frutos_de_cascara,
            R.id.allergens_apio, R.id.allergens_mostaza, R.id.allergens_sesamo, R.id.allergens_sulfitos, R.id.allergens_moluscos,
            R.id.allergens_altramuces])
    List<View> viewsAllergens

    DishView(Context context) {
        super(context, null)

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        inflater.inflate(R.layout.card_dish, this, true)

        SwissKnife.inject(this)

    }

    DishView(Context context, AttributeSet attrs) {
        super(context, attrs)

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        inflater.inflate(R.layout.card_dish, this, true)

        SwissKnife.inject(this)
    }

    void setImageDish(String urlImage) {
        AsyncTask<Object, Integer, Bitmap> task = new ImageLoader(imageDish, urlImage)
        task.execute()
    }

    void setAllergens(AllergensEnums[] allergens){
        for(AllergensEnums allergen : allergens){
            switch (allergen){
                case AllergensEnums.GLUTEN:
                    setAlphaToView(R.id.allergens_gluten)
                    break
                case AllergensEnums.CRUSTACEOS:
                    setAlphaToView(R.id.allergens_crustaceos)
                    break
                case AllergensEnums.ALTRAMUCES:
                    setAlphaToView(R.id.allergens_altramuces)
                    break
                case AllergensEnums.APIO:
                    setAlphaToView(R.id.allergens_apio)
                    break
                case AllergensEnums.CACAHUETES:
                    setAlphaToView(R.id.allergens_cacahuetes)
                    break
                case AllergensEnums.SOJA:
                    setAlphaToView(R.id.allergens_soja)
                    break
                case AllergensEnums.FRUTOS_DE_CASCARA:
                    setAlphaToView(R.id.allergens_frutos_de_cascara)
                    break
                case AllergensEnums.HUEVOS:
                    setAlphaToView(R.id.allergens_huevos)
                    break
                case AllergensEnums.GRANOS_DE_SESAMO:
                    setAlphaToView(R.id.allergens_sesamo)
                    break
                case AllergensEnums.MOLUSCOS:
                    setAlphaToView(R.id.allergens_moluscos)
                    break
                case AllergensEnums.MOSTAZA:
                    setAlphaToView(R.id.allergens_mostaza)
                    break
                case AllergensEnums.PESCADO:
                    setAlphaToView(R.id.allergens_pescado)
                    break
                case AllergensEnums.SULFITOS:
                    setAlphaToView(R.id.allergens_sulfitos)
                    break
                case AllergensEnums.LACTEOS:
                    setAlphaToView(R.id.allergens_lacteos)
                    break
            }
        }
    }
    void setAlphaToView(int res){
        for(View view : viewsAllergens){
            if (view.id == res){
                view.setAlpha(1.0f)
            }
        }
    }

    void setPrice(Float price){
        priceDish.setText(price + " €")
    }

    void setTitleDish(String title) {
        titleDish.setText(title)
    }

    void setDescriptionDish(String description) {
        descriptionDish.setText(description)
    }

    void setQuantity(int quantity){
        if(quantity <= 0){
            quantityText.visibility = INVISIBLE
            quantityDishes.visibility =INVISIBLE
        }else{
            quantityText.visibility = VISIBLE
            quantityDishes.visibility = VISIBLE
            quantityDishes.text = ""+quantity
        }
    }

    private class ImageLoader extends AsyncTask<Object, Integer, Bitmap>{

        private WeakReference<ImageView> image;
        private String url

        public ImageLoader(image, url){
            this.image = new WeakReference<>(image)
            this.url = url
        }

        @Override
        protected Bitmap doInBackground(Object[] params) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null && this.image != null && this.image.get() != null) {
                this.image.get().setImageBitmap(image);
            }
        }
    }
}