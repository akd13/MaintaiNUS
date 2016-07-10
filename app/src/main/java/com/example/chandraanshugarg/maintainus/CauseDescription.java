package com.example.chandraanshugarg.maintainus;

/**
 * Created by akankshitadash on 10/7/16.
 */
public class CauseDescription {

    private int _id;
    private String _cause;
    private String _description;

    public CauseDescription() {
    }

    public CauseDescription(String _cause, String _description) {
        this._cause = _cause;
        this._description = _description;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_cause(String _cause) {
        this._cause = _cause;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_id() {
        return _id;
    }

    public String get_cause() {
        return _cause;
    }

    public String get_description() {
        return _description;
    }
}
