package com.rodrigo.api.controller;

import java.util.ArrayList;

public class ResponseBody {

	private String informacion;
	private Object data;
	private ArrayList<String> errores;
	private ArrayList<Hipermedia> hypermedias;
	
	public ResponseBody() {
		super();
		this.data = new Object();
		this.informacion = "";
		this.errores = new ArrayList<String>();
		this.hypermedias = new ArrayList<Hipermedia>();
	}

	public void addError(String error) {
		this.errores.add(error);
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ArrayList<String> getErrores() {
		return errores;
	}

	public void setErrores(ArrayList<String> errores) {
		this.errores = errores;
	}

	public ArrayList<Hipermedia> getHypermedias() {
		return hypermedias;
	}

	public void setHypermedias(ArrayList<Hipermedia> hypermedias) {
		this.hypermedias = hypermedias;
	}

	@Override
	public String toString() {
		return "ResponseBody [informacion=" + informacion + ", data=" + data + ", errores=" + errores + ", hypermedias="
				+ hypermedias + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((errores == null) ? 0 : errores.hashCode());
		result = prime * result + ((hypermedias == null) ? 0 : hypermedias.hashCode());
		result = prime * result + ((informacion == null) ? 0 : informacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseBody other = (ResponseBody) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (errores == null) {
			if (other.errores != null)
				return false;
		} else if (!errores.equals(other.errores))
			return false;
		if (hypermedias == null) {
			if (other.hypermedias != null)
				return false;
		} else if (!hypermedias.equals(other.hypermedias))
			return false;
		if (informacion == null) {
			if (other.informacion != null)
				return false;
		} else if (!informacion.equals(other.informacion))
			return false;
		return true;
	}
	
	
	
}
