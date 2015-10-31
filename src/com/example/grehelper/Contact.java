package com.example.grehelper;

public class Contact 
{
	 
    //private variables
    int _id;
    String _word;
    String _meaning;
    String _antonim;
    String _synonim;
 
    // Empty constructor
    public Contact()
    {
 
    }
    
    // constructor
    public Contact(int id, String word, String meaning,String antonim,String synonim)
    {
        this._id = id;
        this._word = word;
        this._meaning = meaning;
        this._antonim = antonim;
        this._synonim = synonim;
    }
 
    // constructor
    public Contact(String word, String meaning,String antonim,String synonim)
    {
        this._word = word;
        this._meaning = meaning;
        this._antonim = antonim;
        this._synonim = synonim;
    }
	
    public int get_id() 
    {
		return _id;
	}
	
    public void set_id(int _id) 
    {
		this._id = _id;
	}
	
    public String get_word() 
    {
		return _word;
	}
	
    public void set_word(String _word) 
    {
		this._word = _word;
	}
	
    public String get_meaning() 
    {
		return _meaning;
	}
	
    public void set_meaning(String _meaning)
    {
		this._meaning = _meaning;
	}
	
    public String get_antonim() 
    {
		return _antonim;
	}
	
    public void set_antonim(String _antonim) 
    {
		this._antonim = _antonim;
	}
	
    public String get_synonim() 
    {
    	
		return _synonim;
	}
	
    public void set_synonim(String _synonim) 
    {
		this._synonim = _synonim;
	}
 
}