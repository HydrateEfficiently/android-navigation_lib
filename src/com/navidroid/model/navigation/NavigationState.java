package com.navidroid.model.navigation;

import java.util.List;

import com.navidroid.model.LatLng;
import com.navidroid.model.directions.Direction;
import com.navidroid.model.directions.Directions;
import com.navidroid.model.directions.Point;
import com.navidroid.model.positioning.Position;

public class NavigationState {
	
	protected List<Point> path;
	protected Directions directions;
	protected LatLng destination;
	protected Position position;
	protected LatLng locationOnPath;
	protected double bearingOnPath;
	protected double distanceOffPath;
	protected double bearingDifferenceFromPath;
	protected Point currentPoint;
	protected double distanceToNextPoint;
	protected double progressAlongSegment;
	protected boolean isHeadingOffPath;
	protected boolean isOnPath;
	protected long headingOffPathStartTime;
	protected boolean hasDeparted;
	protected boolean hasStartedFollowingDirections;
	protected boolean hasArrived;
	
	public NavigationState() { }
	
	protected NavigationState(NavigationState navigationStateSnapshot) {
		this.path = navigationStateSnapshot.path;
		this.directions = navigationStateSnapshot.directions;
		this.destination = navigationStateSnapshot.destination;
		this.position = navigationStateSnapshot.position;
		this.locationOnPath = navigationStateSnapshot.locationOnPath;
		this.bearingOnPath = navigationStateSnapshot.bearingOnPath;
		this.distanceOffPath = navigationStateSnapshot.distanceOffPath;
		this.bearingDifferenceFromPath = navigationStateSnapshot.bearingDifferenceFromPath;
		this.currentPoint = navigationStateSnapshot.currentPoint;
		this.distanceToNextPoint = navigationStateSnapshot.distanceToNextPoint;
		this.progressAlongSegment = navigationStateSnapshot.progressAlongSegment;
		this.isHeadingOffPath = navigationStateSnapshot.isHeadingOffPath;
		this.isOnPath = navigationStateSnapshot.isOnPath;
		this.headingOffPathStartTime = navigationStateSnapshot.headingOffPathStartTime;
		this.hasDeparted = navigationStateSnapshot.hasDeparted;
		this.hasStartedFollowingDirections = navigationStateSnapshot.hasStartedFollowingDirections;
		this.hasArrived = navigationStateSnapshot.hasArrived;
	}
	
	public LatLng getDestination() {
		return destination;
	}
	
	public Point getCurrentPoint() {
		return currentPoint;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public LatLng getLocation() {
		return position.location;
	}
	
	public double getBearing() {
		return position.bearing;
	}
	
	public long getTime() {
		return position.timestamp;
	}
	
	public LatLng getLocationOnPath() {
		return locationOnPath;
	}
	
	public double getBearingOnPath() {
		return bearingOnPath;
	}
	
	public double getDistanceOffPath() {
		return distanceOffPath;
	}
	
	public double getBearingDifferenceFromPath() {
		return bearingDifferenceFromPath;
	}
	
	public boolean isHeadingOffPath() {
		return isHeadingOffPath;
	}
	
	public boolean isOnPath() {
		return isOnPath;
	}
	
	public long getHeadingOffPathStartTime() {
		return headingOffPathStartTime;
	}
	
	public Directions getDirections() {
		return directions;
	}
	
	public Direction getCurrentDirection() {
		return currentPoint == null ? null : currentPoint.direction; 
	}
	
	public Direction getNextDirection() {
		return currentPoint == null ? null : currentPoint.nextDirection;
	}
	
	public double getDistanceToCurrentDirection() {
		if (!isNavigating()) {
			return 0;
		}
		
		Point nextPoint = currentPoint.next;
		return nextPoint == null ? 0 : distanceToNextPoint + nextPoint.distanceToCurrentDirectionMeters;
	}
	
	public double getDistanceToNextDirection() {
		if (!isNavigating()) {
			return 0;
		}
		
		Point nextPoint = currentPoint.next;
		return nextPoint == null ? 0 : distanceToNextPoint + nextPoint.distanceToNextDirectionMeters;
	}
	
	public double getDistanceToArrival() {
		if (!isNavigating()) {
			return 0;
		}
		
		Point nextPoint = currentPoint.next;
		return nextPoint == null ? 0 : distanceToNextPoint + nextPoint.distanceToArrivalMeters;
	}
	
	public double getTimeToCurrentDirection() {
		if (!isNavigating()) {
			return 0;
		}
		
		Point nextPoint = currentPoint.next;
		double timeFromNextPoint = nextPoint == null ? 0 : nextPoint.timeToCurrentDirectionSeconds;
		return progressAlongSegment * currentPoint.timeToCurrentDirectionSeconds + timeFromNextPoint;
	}
	
	public double getTimeToNextDirection() {
		if (!isNavigating()) {
			return 0;
		}
		
		Point nextPoint = currentPoint.next;
		double timeFromNextPoint = nextPoint == null ? 0 : nextPoint.timeToNextDirectionSeconds;
		return progressAlongSegment * currentPoint.timeToCurrentDirectionSeconds + timeFromNextPoint;
	}
	
	public double getTimeToArrival() {
		if (!isNavigating()) {
			return 0;
		}
		
		Point nextPoint = currentPoint.next;
		double timeToNextPoint = progressAlongSegment * currentPoint.timeToCurrentDirectionSeconds;
		return nextPoint == null || currentPoint.direction != nextPoint.direction ? timeToNextPoint :
			timeToNextPoint + nextPoint.timeToArrivalSeconds;
	}
	
	public boolean isNavigating() {
		return path != null;
	}
	
	public boolean hasDeparted() {
		return hasDeparted;
	}
	
	public boolean hasStartedFollowingDirections() {
		return hasStartedFollowingDirections;
	}
	
	public boolean hasArrived() {
		return hasArrived;
	}
}
