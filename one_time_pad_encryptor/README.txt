********************************
One-Time Pad Encryptor/Decryptor
********************************

DESCRIPTION:
------------
A simple C program for encryption and decryption using a one-time pad.

USAGE:
------
1. Compile. Example: (using gcc) "gcc -o otp otp.c"
2. Create a cleartext file to encrypt and a one-time pad/key file to
   XOR with the cleartext. The pad file should be at least as many
   bytes as the cleartext file. The pad can be longer but the extra
   bytes will not be used. During encryption and decryption, the two
   files are XORed character by character until one of the files ends.
3. Encrypt with: "otp <cleartext filename> <pad filename>".
   Decrypt with: "otp <ciphertext filename> <pad filename>".
   The cleartext/ciphertext/pad filenames can come in any order when
   encrypting/decrypting because the XOR operation is symmetric.
4. A file named "out" will be created with the resulting ciphertext
   or cleartext. Remember to change the filename of "out" when
   decrypting, otherwise the program will be trying to read and write
   to the same file. 
