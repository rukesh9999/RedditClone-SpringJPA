package com.ReditClone.model;

import java.io.Serializable;

public enum VoteType implements Serializable{
  
	UPVOTE(1), DOWNVOTE(-1);
	
	VoteType(int direction)
	{
		
	}
}
