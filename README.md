# movie-night
Project 0
Instructions:

Write a program that manages a theatre seating reservation system.

Keep a binary file for each room, we have theatres 'Harry Potter', 'Encanto', and 'Matrix'.

theatre for Harry Potter has 10 seats, theatre for Encanto has 20 seats and theatre for Matrix has 15 seats.

The program has two options, guest reservations or manager report.

Allow a guest to attempt to reserve a seat in a given theatre, ask them which theatre and which seat number, and see if that seat number is available.  If it is available, write their name in the file for that seat, generate a random confirmation code ( random number 10,000-100,000 ) and write the confirmation code in the file as well. Be sure to tell the guest the code as well.  If the seat is already taken, let them know they can't have that seat and allow them to pick another.

Add an option for a 'manager' to print a theatre reservation list, ask which theatre, which shows which seat #s are taken, the guest name and the confirmation code for each guest.  Be sure to show empty seats as empty with no code.
