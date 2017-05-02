//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
/** @author  Khalid Jahangeer
 *  @version 1.3
 *  @date    Mon Feb  20 13:55:22 EST 2017
 *  @see     LICENSE (MIT style license file).
 *
 *  This code has been written as a solution to problem #54 from Project Euler
 *  https://projecteuler.net/problem=54
 *  https://www.hackerrank.com/contests/projecteuler/challenges/euler054
 */

import java.util.*;
import java.io.*;
public class PokerHand {

	HashMap <Character,Integer> card_value = new HashMap<Character,Integer>();
	//constructor to initialize the card_value of each card
	PokerHand()
	{
		card_value.put('2',2);
		card_value.put('3',3);
		card_value.put('4',4);
		card_value.put('5',5);
		card_value.put('6',6);
		card_value.put('7',7);
		card_value.put('8',8);
		card_value.put('9',9);
		card_value.put('T',10);
		card_value.put('J',11);
		card_value.put('Q',12);
		card_value.put('K',13);
		card_value.put('A',14);

	}

	public static void main(String[] args) throws IOException {

		String FILENAME = "/Users/khalidjahangeer/Documents/workspace/ProjectEulerPokerHands/p054_poker.txt";

		BufferedReader br = null;
		String sCurrentLine;
		int count = 0;

		br = new BufferedReader(new FileReader(FILENAME));
		int j = 0;
		String [] inputarray = new String[1000];
		while ((sCurrentLine = br.readLine()) != null) {
			inputarray[j] = sCurrentLine.trim();
			j++;
		}

		/*String [] inputarray = { "5H 7S 6S 5C KD 2C 3S 8S 8D TD",
                                 "5D 8C 9S JS AC 2C 5C 7D 8S QH",
                                 "2D 9C AS AH AC 3D 6D 7D TD QD",
                                 "4D 6S 9H QH QC 3D 6D 7H QD QS",
                                 "4S 2D 4C 4D 2H 3C 3D 3S 9S 9D"};

		 */

		for ( int i = 0 ; i < inputarray.length ; i++)
		{
			//String hand = input.nextLine().trim();         // read the hand , trim leading and trailing white spaces
			String hand = inputarray[i].trim();
			//System.out.println(hand);

			if (hand.length() != 29)
				System.out.println("Invalid hand");
			else
			{
				String player1 = "";
				String player2 = "";
				player1 = (player1 + hand.substring(0,hand.length()/2)).trim();
				player2 = (player2 + hand.substring(hand.length()/2+1)).trim();

				PokerHand obj = new PokerHand();
				String res = (obj.test(player1,player2));

				if (res.equals("Player 1"))
					count++;
			}

		}// for
		System.out.println(count);
		br.close();
	} //main

	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** Test Function to call all the methods for testing the hands 
	 */ 
	public String test( String p1 , String p2)
	{
		// convert to array of Strings to facilitate sorting
		String [] play1 = p1.split("\\s");
		String [] play2 = p2.split("\\s");

		// arrange the cards in ascending order according to 
		// 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace

		play1 = sort(play1);
		play2 = sort(play2);


		//1. Royal Flush : return 0 is Yes 1 if No
		int check1 = RoyalFlush(play1);
		int check2 = RoyalFlush(play2);

		if(check1 == 0 && check2 == 1)
		{ 
			return "Player 1"; }
		else if (check1 == 1 && check2 == 0)
		{ 
			return "Player 2"; }
		else{ 
			
			check1 = StraightFlush(play1);
			check2 = StraightFlush(play2);
			
			if(check1 == 0 && check2 == 1)
			{ 
				return "Player 1"; }
			else if (check1 == 1 && check2 == 0)
			{ 
				return "Player 2"; }
			else if (check1 == 0 && check2 == 0)
			{
				
				if(card_value.get(play1[4].charAt(0)) > card_value.get(play2[4].charAt(0)))
				{  
					return "Player 1"; }
				else
				{ 
					return "Player 2"; }
			}
			else
			{
				check1 = FourofaKind(play1);
				check2 = FourofaKind(play2);
				if(check1 == 0 && check2 == 1)
				{
					return "Player 1"; }
				else if (check1 == 1 && check2 == 0)
				{  
					return "Player 2"; }
				else if (check1 == 0 && check2 == 0)
				{
					
					if(card_value.get(play1[2].charAt(0)) > card_value.get(play2[2].charAt(0)))
					{ 
						return "Player 1"; }
					else
					{  
						return "Player 2"; }
				}
				else
				{
					check1 = FullHouse(play1);
					check2 = FullHouse(play2);
					if(check1 == 0 && check2 == 1)
					{  
						return "Player 1"; }
					else if (check1 == 1 && check2 == 0)
					{ 
						return "Player 2"; }
					else if (check1 == 0 && check2 == 0)
					{
				// check highest card in case both four of a kind the middle card will be decider
						if(card_value.get(play1[2].charAt(0)) > card_value.get(play2[2].charAt(0)))
						{ 
							return "Player 1"; }
						else
						{ 
							return "Player 2"; }
					}
					else
					{
						check1 = Flush(play1);
						check2 = Flush(play2);
						if(check1 == 0 && check2 == 1)
						{ 
							return "Player 1"; }
						else if (check1 == 1 && check2 == 0)
						{ 
							return "Player 2"; }
						else if (check1 == 0 && check2 == 0)
						{
							
							if(card_value.get(play1[4].charAt(0)) > card_value.get(play2[4].charAt(0)))
							{ 
								return "Player 1"; }
							else
							{ 
								return "Player 2"; }
						}
						else
						{
							check1 = Straight(play1);
							check2 = Straight(play2);
							if(check1 == 0 && check2 == 1)
								return "Player 1"; 
							else if (check1 == 1 && check2 == 0)
								return "Player 2"; 
							else if (check1 == 0 && check2 == 0)
							{
				// check highest card in case both have Straight then highest card will be decider
								if(play1[4].charAt(0) == 'A' && play2[4].charAt(0) == 'A' )
								{
						if(card_value.get(play1[3].charAt(0)) > card_value.get(play2[3].charAt(0)))
									return "Player 1"; 
									else
									return "Player 2"; 
								}
								else
								{
						if(card_value.get(play1[4].charAt(0)) > card_value.get(play2[4].charAt(0)))
										return "Player 1"; 
									else
										return "Player 2"; 
								}
							}
							else
							{
	
								check1 = ThreeofaKind(play1);
								check2 = ThreeofaKind(play2);
								if(check1 == 0 && check2 == 1)
									return "Player 1"; 
								else if (check1 == 1 && check2 == 0)
									return "Player 2"; 
								else if (check1 == 0 && check2 == 0)
								{
		        // check highest card in case both have Three of a Kind then highest card will be decider
						if(card_value.get(play1[2].charAt(0)) > card_value.get(play2[2].charAt(0)))
									return "Player 1"; 
									else
									return "Player 2"; 
								}
								else
								{
									int check1_arr [][] = Pair(play1);
									int check2_arr [][] = Pair(play2);

									int sum1 = check1_arr[0][1] + check1_arr[1][1];
									int sum2 = check2_arr[0][1] + check2_arr[1][1];
				if(sum1 > sum2)  // p1 has 2 pair and p2 has 1 pair or p1 has a pair and p2 doesnt
										return "Player 1";
									else if (sum2 > sum1)
										return "Player 2";
									else 
									{

										if (sum1 == 1  && sum2 ==1)   // both have one pair
										{

											if(card_value.get(play1[check1_arr[0][0]].charAt(0)) > card_value.get(play2[check2_arr[0][0]].charAt(0)))
											{ 
												return "Player 1";}
											else if(card_value.get(play1[check1_arr[0][0]].charAt(0)) < card_value.get(play2[check2_arr[0][0]].charAt(0)))
											{ 
												return "Player 2";}
											else
											{
												ArrayList<String> arr1 = new ArrayList<String>();
												ArrayList<String> arr2 = new ArrayList<String>();
												for(int i = 0 ; i< 5;i++)
												{
													if(play1[i].charAt(0) != play1[check1_arr[0][0]].charAt(0))
														arr1.add(play1[i]);
													if(play2[i].charAt(0) != play2[check2_arr[0][0]].charAt(0))
														arr2.add(play2[i]);
												} //for
												int flag = 0;
												for(int i = arr1.size()-1; i >=0;i--)
												{
													if(card_value.get(arr1.get(i).charAt(0)) > card_value.get(arr2.get(i).charAt(0)) )
													{ flag = 1; break; }
													if(card_value.get(arr1.get(i).charAt(0)) < card_value.get(arr2.get(i).charAt(0)) )
													{ flag = 2; break; }

												} //for

												if (flag == 1)
													return "Player 1";
												else
													return "Player 2";
											} //else

										}
										else  if (sum1 == 2  && sum2 ==2)   // both have two pairs
										{
											if(card_value.get(play1[check1_arr[1][0]].charAt(0)) > card_value.get(play2[check2_arr[1][0]].charAt(0)))
												return "Player 1";
											else if(card_value.get(play1[check1_arr[1][0]].charAt(0)) < card_value.get(play2[check2_arr[1][0]].charAt(0)))
												return "Player 2";
											else
											{
												int a =0; int b =0;
												for(int i = 0 ; i< 5;i++)
												{
													if(play1[i].charAt(0) != play1[check1_arr[0][0]].charAt(0) && play1[i].charAt(0) != play1[check1_arr[1][0]].charAt(0))
														a = card_value.get(play1[i].charAt(0));
													if(play2[i].charAt(0) != play2[check2_arr[0][0]].charAt(0) && play2[i].charAt(0) != play2[check2_arr[1][0]].charAt(0))
														b = card_value.get(play2[i].charAt(0));
												} //for

												if (a > b)
													return "Player 1";
												else
													return "Player 2";
											}
										}
										else
										{
											// return high card
											ArrayList<String> arr1 = new ArrayList<String>();
											ArrayList<String> arr2 = new ArrayList<String>();
											for(int i = 0 ; i< 5;i++)
											{
												arr1.add(play1[i]);
												arr2.add(play2[i]);
											} //for
											int flag = 0;
											for(int i = arr1.size()-1; i >=0;i--)
											{
												if(card_value.get(arr1.get(i).charAt(0)) > card_value.get(arr2.get(i).charAt(0)) )
												{ flag = 1; break; }
												if(card_value.get(arr1.get(i).charAt(0)) < card_value.get(arr2.get(i).charAt(0)) )
												{ flag = 2; break; }

											} //for
											if (flag == 1)
											{ 
												return "Player 1";}
											else
											{ 
												return "Player 2";}
										}
									}

								} //else
							} // else

						} // else
					} // else

				} //else

			} //else
		}  //else
	} //test

	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** Sort Function to Sort the cards according to their value
	 * first put all the cards related to a particular value in list fashion inside the HashMap
	 * eg. 4S 2D 4C 4D 2H will be
	 * 4 [4S, 4C , 4D]
	 * 2 [2D, 2H]
	 * We can now normally sort the cards according to key
	 * Retrieve the values and recreate the Hand in sorted fashion
	 */ 
	public String[] sort(String [] play)
	{

		String [] sorted = new String[5];
		HashMap <Integer,ArrayList<String>> equivalent = new HashMap<Integer,ArrayList<String>>();
		// we will first put numerical equivalents from the values HashMap while preserving card values
		for(int i = 0 ; i <5 ; i ++)
		{
			int value = card_value.get(play[i].charAt(0));
			// check if value exists in map 
			if (equivalent.containsKey(value))
			{
				ArrayList<String> temp = equivalent.get(value);
				temp.add(play[i]);
				equivalent.put(value, temp);
			} // if
			else
			{
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(play[i]);
				equivalent.put(value, temp);
			} //else
		} // for

		ArrayList<Integer> sorting = new ArrayList<Integer>();
		Iterator it = equivalent.keySet().iterator();
		while(it.hasNext())
		{ 
			int key = it.next().hashCode();
			sorting.add(key);
		}

		Collections.sort(sorting);

		//System.out.println(sorting.toString());
		int i = 0;
		for ( int num : sorting)
		{
			ArrayList<String> temp = equivalent.get(num);
			//System.out.println(temp.toString());
			for (String card : temp)
			{
				sorted[i] = card;
				i++;
			}
		}
		//System.out.println(Arrays.toString(sorted));
		equivalent.clear();
		return sorted;
	} // sort
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** RoyalFlush Function to check if the cards satisfy the Royal Flush category
	 *  Royal Flush : Ten, Jack, Queen, King, Ace, in same suit.
	 */ 
	public int RoyalFlush(String [] play)
	{
		// check if all cards have same suit 0 : Yes 1 : NO
		int val = Flush(play);
		if (val == 1)
			return 1;
		else
		{
			// check for 10 , Jack , Queen , King , Ace
			if (play[0].charAt(0) == 'T' && play[1].charAt(0) == 'J' && play[2].charAt(0) == 'Q' && play[3].charAt(0) == 'K' && play[4].charAt(0) == 'A')
				return 0; 
			else
				return 1;
		}
	} //RoyalFlush

	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** StrightFlush Function to check if the cards satisfy the Straight Flush category
	 * Straight Flush : All cards are consecutive card_value of same suit.
	 */ 	
	public int StraightFlush(String [] play)
	{
		// check if all cards have same suit 0 : Yes 1 : NO
		int val = Flush(play);
		if (val == 1)
			return 1;
		else
		{
			// check for straight
			int value = Straight(play);
			if (value == 0)
				return 0; 
			else
				return 1;

		} // if
	} //StraightFlush
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** Straight Function to check if the cards satisfy the Straight category
	 * Straight  : All cards are consecutive card_value.
	 */ 	
	public int Straight(String [] play)
	{
		// check for consecutive numbers
		int flag = 0;
		for ( int i = 1 ; i <5 ;i++)
		{
			if(card_value.get(play[i].charAt(0)) - card_value.get(play[i-1].charAt(0)) != 1)
			{ flag = 1;
			break;
			}
		}// for
		if ( flag == 1 )    // check for Special case A,2,3,4,5
		{ 
			if(card_value.get(play[0].charAt(0)) == 2 && card_value.get(play[1].charAt(0)) == 3 && card_value.get(play[2].charAt(0)) == 4 && card_value.get(play[3].charAt(0)) == 5 && card_value.get(play[4].charAt(0)) == 14)
				flag = 0;
		}
		return flag;

	} //Straight

	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** Flush Function to check if the cards satisfy the Flush category
	 * Flush : All cards of the same suit.
	 */ 
	public  int Flush(String [] play)
	{
		int flag = 0 ;
		char suit = play[0].charAt(1);
		for ( int i = 1 ; i < play.length ; i++)
		{
			if(play[i].charAt(1) != suit)
			{
				flag = 1;
				break;
			}
		}
		return flag;
	} //Flush

	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** ThreeofaKind Function to check if the cards satisfy the FourofaKind category
	 * Three of a Kind : Three Cards with the same value.
	 */ 
	public  int ThreeofaKind(String [] play)
	{
		int flag = 1 ;

		if ((play[0].charAt(0) == play[1].charAt(0)) && (play[1].charAt(0) == play[2].charAt(0)) || ((play[1].charAt(0) == play[2].charAt(0)) && play[2].charAt(0) == play[3].charAt(0)) || 
				((play[2].charAt(0) == play[3].charAt(0)) && play[3].charAt(0) == play[4].charAt(0)))
			flag = 0;

		return flag;
	} //ThreeofaKind

	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** Full House Function to check if the cards satisfy the Full House category
	 * Full House : Three Cards with the same value and Two cards that are same.
	 */ 
	public  int FullHouse(String [] play)
	{
		int flag = 1 ;

		if (((play[0].charAt(0) == play[1].charAt(0)) && (play[1].charAt(0) == play[2].charAt(0)) && (play[3].charAt(0) == play[4].charAt(0))) || 
				((play[2].charAt(0) == play[3].charAt(0) && (play[3].charAt(0) == play[4].charAt(0)) && (play[0].charAt(0) == play[1].charAt(0)))))
			flag = 0;

		return flag;
	} //FourofaKind

	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** FourofaKind Function to check if the cards satisfy the FourofaKind category
	 * Four of a Kind : Four Cards with the same value.
	 */ 
	public  int FourofaKind(String [] play)
	{
		int flag = 1 ;

		if ((play[0].charAt(0) == play[1].charAt(0) && play[1].charAt(0) == play[2].charAt(0) && play[2].charAt(0) == play[3].charAt(0)) || 
				(play[1].charAt(0) == play[2].charAt(0) && play[2].charAt(0) == play[3].charAt(0) && play[3].charAt(0) == play[4].charAt(0)))
			flag = 0;

		return flag;
	} //FourofaKind


	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/** Pair Function to check if the cards have existing pairs
	 *  return the count of pairs found and the position of the paired element
	 */ 
	public  int [][] Pair(String [] play)
	{
		//System.out.println(Arrays.toString(play));
		int pos_count[][] = new int[2][2] ;
		int j = 0 ;
		for( int i = 1 ; i < 5 ; i ++)
		{
			if (play[i].charAt(0) == play[i-1].charAt(0))
			{
				pos_count[j][0] = i;
				pos_count[j][1] = pos_count[j][1] +1;
				j++;
			}

		}

		return pos_count;
	} //Pair

} //class
