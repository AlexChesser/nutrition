package com.iifymapp.iifym.Model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iifymapp.iifym.DatabaseHelper;
import com.iifymapp.iifym.R;

public class NutritionData extends DatabaseHelper {
	public class Nutrient {
		public String description;
		public String Value;
		public Float BaseValue;
		public String Units;
		public String No;
		public String Decimals;
		
		public void setValueText(){
			
		}
		
		@Override
		public String toString(){
			return description + " " + Value + Units;
		} 
	}

	public class Weight{
		public Float Amount;
		public String Desc;
		public Float Grams;
		
		@Override
		public String toString(){
			return Amount + " " + Desc;
		}
	}
	
	public class Food {
		public ArrayList<Nutrient> Nutrients;
		public ArrayList<Weight> Weights;
		public String id;  
		public String NdbNo;
		public String Group;
		public String LongDesc;

		public Food init(String foodID){
			this.id = foodID;
			Connect();
			String sql = "SELECT f.Ndb_No, f.Long_Desc, fg.FdGrp_Desc FROM NUT_DENORM f INNER JOIN FD_GROUP fg ON f.FdGrp_Cd = fg.FdGrp_Cd WHERE f._id = '"+this.id+"'";
	        Cursor c = sqdb.rawQuery(sql, null);
	        if (c != null) {
	            c.moveToFirst();
	            this.NdbNo = c.getString(c.getColumnIndex("NDB_No"));
	            this.LongDesc = c.getString(c.getColumnIndex("Long_Desc"));
	        }
	        close();
	        c.close();
	        sqdb.close();
	        return this;
		}
		
		public void setWeight(Float g){
			Float multiplier = g / 100;
			for (Nutrient n : this.Nutrients) {
				n.Value = String.valueOf(Float.parseFloat(n.Value) * multiplier);
			}
		}
		
		public ArrayList<Weight> getWeights(){
			if (this.Weights == null){
				this.Weights = new ArrayList<Weight>();
				Connect();
				String sql = "SELECT * FROM WEIGHT WHERE Ndb_No = '"+this.NdbNo+"'";
				
		        Cursor c = sqdb.rawQuery(sql, null);
		        if (c != null) {
		            c.moveToFirst();
		            while (c.isAfterLast() == false) 
		            {
		            	Weight w = new Weight();
		            	w.Amount = c.getFloat(c.getColumnIndex("Amount"));
		            	w.Desc = c.getString(c.getColumnIndex("Msre_Desc"));
		            	w.Grams = c.getFloat(c.getColumnIndex("Gm_Wgt"));
		            	this.Weights.add(w);
		            	c.moveToNext();
		            }
		        }
		        close();
		        c.close();
		        sqdb.close();
	        	Weight w = new Weight();
	        	w.Amount = 100.00f;
	        	w.Desc = "grams";
	        	w.Grams = 100.00f;
	        	this.Weights.add(w);
	        	
	        	w = new Weight();
	        	w.Amount = 1.00f;
	        	w.Desc = "pound";
	        	w.Grams = 450.00f;
	        	this.Weights.add(w);
			}

        	
			return this.Weights;
		}

		public ArrayList<Nutrient> resetNutrientBase(){
			if (this.Nutrients == null){
				this.Nutrients = new ArrayList<Nutrient>();
			}
			this.Nutrients = FoodData(this.id);
			return this.Nutrients;
		}
		
		public ArrayList<Nutrient> getNutrients(){
			if (this.Nutrients == null){
				this.Nutrients = new ArrayList<Nutrient>();
				this.Nutrients = FoodData(this.id);
			}
			return this.Nutrients;
		}		
		
	}
	
	public class NutrientAdapter extends ArrayAdapter<Nutrient> {

	    private ArrayList<Nutrient> items;
	    private Context context;

	    public NutrientAdapter(Context context, int textViewResourceId, ArrayList<Nutrient> items) {
	        super(context, textViewResourceId, items);
	        this.context = context;
	        this.items = items;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.nutrient_row, null);
	        }

	        Nutrient item = items.get(position);
	        if (item!= null) {
	            TextView itemView = (TextView) view.findViewById(R.id.Units);
	            itemView.setText(String.format("%s", item.Units));
	            
	            itemView = (TextView) view.findViewById(R.id.Description);
	            itemView.setText(String.format("%s", item.description));
	            
	            itemView = (TextView) view.findViewById(R.id.Value);
	            itemView.setText(String.format("%s", item.Value));
	         }

	        return view;
	    }
	}	
	
	public NutritionData(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Nutrient> FoodData(String foodID){
		Connect();
		
		Cursor DEF = sqdb.rawQuery("SELECT *, '' as VAL FROM NUTR_DEF ORDER BY cast(SR_Order as int) ASC", null);
        if (DEF != null) {
            DEF.moveToFirst();
        }		
		
		String sql = "SELECT * FROM NUT_DENORM WHERE _id = '"+foodID+"'";
        Cursor c = sqdb.rawQuery(sql, null);
        if (c != null) {
            c.moveToFirst();
        }
		
        ArrayList<Nutrient> nutrients =  new ArrayList<Nutrient>();
        while (DEF.isAfterLast() == false) 
        {
            String NutrNo = "_"+DEF.getString(DEF.getColumnIndex("Nutr_No"));
        	String NutrDesc = DEF.getString(DEF.getColumnIndex("NutrDesc"));
        	String Value = c.getString(c.getColumnIndex(NutrNo));
        	String Num_Dec = DEF.getString(DEF.getColumnIndex("Num_Dec"));
        	String Units = DEF.getString(DEF.getColumnIndex("Units"));
        	
        	
        	Nutrient nutrient = new Nutrient(); 
        	nutrient.No = NutrNo;
        	nutrient.description = NutrDesc;
        	nutrient.Value = Value;
        	nutrient.Units = Units;
        	nutrient.Decimals = Num_Dec;
        	
        	if (Value != null) {
            	nutrient.BaseValue = Float.parseFloat(Value);
        		nutrients.add(nutrient);
        	}

            DEF.moveToNext();
        }
        
        close();
        c.close();
        DEF.close();
        sqdb.close();
        return nutrients;
	}
	
	public Cursor FoodSearch(String searchterm){
		Connect();

        String where = "";
        String from = "FROM NUT_DENORM fd ";
        if (!searchterm.trim().equals("")) {
        	where = "WHERE fts.Long_Desc MATCH '"+searchterm.trim()+"*'";
        	from =  " FROM LongDescFTS fts INNER JOIN NUT_DENORM fd ON fts._id = fd._id ";
        }
        
        String sql = "	SELECT   fd._id, " +
        						"fg.FdGrp_Desc, " +
        						"fd.Long_Desc, " +
        						"ifnull(fd._208,0) as kcal, " +
        						"cast(ifnull(fd._203,0) as int) || 'g' as Pro, " +
        						"cast(ifnull(fd._204,0) as int) || 'g' as Fat, " +
        						"cast(ifnull(fd._205,0) as int) || 'g' as Carb, " +
        						"cast(ifnull(fd._291,0) as int) || 'g' as Fibre " +
        				from +
        				"INNER JOIN FD_GROUP fg ON fd.FdGrp_Cd = fg.FdGrp_Cd " +
        			  where +
        			" ORDER BY FdGrp_Desc, fd.Long_Desc ASC";
        //android.util.Log.i("SQL", sql);
        // Execute query
        Cursor c = sqdb.rawQuery(sql, null);
        if (c != null) {
            c.moveToFirst();
        }
        close();
        sqdb.close();
        return c;
	}
	
}
