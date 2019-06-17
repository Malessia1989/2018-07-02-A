package it.polito.tdp.extflightdelays.model;

public class Rotta {
	Airport partenza;
	Airport arrivo;
	double mediaMedia;
	public Rotta(Airport partenza, Airport arrivo, double mediaMedia) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
		this.mediaMedia = mediaMedia;
	}
	
	
	public Airport getPartenza() {
		return partenza;
	}


	public void setPartenza(Airport partenza) {
		this.partenza = partenza;
	}


	public Airport getArrivo() {
		return arrivo;
	}


	public void setArrivo(Airport arrivo) {
		this.arrivo = arrivo;
	}


	public double getMediaMedia() {
		return mediaMedia;
	}


	public void setMediaMedia(double mediaMedia) {
		this.mediaMedia = mediaMedia;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrivo == null) ? 0 : arrivo.hashCode());
		result = prime * result + ((partenza == null) ? 0 : partenza.hashCode());
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
		Rotta other = (Rotta) obj;
		if (arrivo == null) {
			if (other.arrivo != null)
				return false;
		} else if (!arrivo.equals(other.arrivo))
			return false;
		if (partenza == null) {
			if (other.partenza != null)
				return false;
		} else if (!partenza.equals(other.partenza))
			return false;
		return true;
	}
	
	

}
