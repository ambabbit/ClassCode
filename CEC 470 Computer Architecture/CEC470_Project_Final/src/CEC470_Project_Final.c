/*
 ============================================================================
 Name        : CEC470_Project_Final.c
 Author      : Adam
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

uint16_t PC=0;
uint8_t IR=0;
uint16_t MAR=0;
uint8_t ACC=0;
uint8_t memory[65536];

void readInMemory();
void printMemory();
void test();
void fetchNextInstruction(void);

int main(void) {
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */
	readInMemory();
	fetchNextInstruction();
	return 0;
}

void printMemory() {
	for (int i = 1; i<=5000; i++) {
		printf("%02x ", memory[i-1]);
		if (i%10 == 0) printf("\n");
	}

}

void readInMemory() {
	int count = 0;
	char str[255];
	FILE* fp = fopen("mem_in.txt", "r");
	if (!fp) {printf("EMPTY %s", str);}

	while(fgets(str, 4,fp)!= NULL) {
		int readInValue = (int) strtol(str,NULL,16);
		memory[count++] = readInValue;
	}
	fclose(fp);
}

void fetchNextInstruction(void) {
	int instruction = 0x00;//memory[PC];
	int mathBits = (instruction/16)-8;
	bool MSB = instruction > 127;
	bool branchBit = instruction > 15;
	char inst[255];
	switch (MSB) {
		case 1:
			if (mathBits == 0) {
				printf("AND ");
			} else if (mathBits == 1) {
				printf("OR ");
			} else if (mathBits == 2) {
				printf("XOR ");
			} else if (mathBits == 3) {
				printf("ADD ");
			} else if (mathBits == 4) {
				printf("SUB ");
			} else if (mathBits == 5) {
				printf("INC ");
			} else if (mathBits == 6) {
				printf("DEC ");
			} else if (mathBits == 7) {
				printf("NOT ");
			}

			if (instruction%16 > 12) {
				printf("MEMORY ");
			} else if (instruction%16 > 8) {
				printf("MAR ");
			} else if (instruction%16 > 4) {
				printf("ACC ");
			} else {
				printf("Indirect ");
			}

			if (instruction %4 == 0) {
				printf("MAR");
			} else if (instruction %4 == 1) {
				printf("ACC");
			} else if (instruction %4 == 2) {
				printf("Constant");
			} else if (instruction %4 == 3) {
				printf("Memory");
			}
			break;
		case 0:

			switch (branchBit) {
			case 1:
				printf("branch");
				break;
			case 0:
				if (instruction > 7)
				{
					printf("LOAD ");
				} else {
					printf("STOR ");
				}

				if (instruction%8 > 3) {
					printf("INDEX ");
				} else {
					printf("ACC");
				}

				if (instruction%4 == 0) {
					printf("Address");
				} else if (instruction%4 == 1) {
					printf("Constant");
				} else {
					printf("MAR");
				}
				break;
		}
	}
}
