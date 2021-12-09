package com.mycompany.a4;

public interface ICollider {
	boolean collidesWith(ICollider collideObject);
	void handleCollision(ICollider collideObject);
}
