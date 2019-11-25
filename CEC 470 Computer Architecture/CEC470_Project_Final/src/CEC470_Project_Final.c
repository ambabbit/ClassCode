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

uint16_t PC;
uint8_t IR;
uint16_t MAR;
uint8_t ACC;
unsigned char memory[65536];

void readInMemory();
void printMemory();
void test();

int main(void) {
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */
	readInMemory();
//	printMemory();
	return 0;
}

void printMemory() {
	printf("\n");
	for (int i = 0; i < 5000; i+=2) {
		printf("%c%c ", memory[i], memory[i+1]);
	}
}

void readInMemory() {
	char str[255];
	FILE* fp = fopen("mem_in.txt", "r");
	if (!fp) {printf("EMPTY %s", str);}

	while(fgets(str, 2,fp)!= NULL) {

		printf("Hex: %s",str);

//		int test = (int)strtol(str,NULL,16);
		if (strcmp(str," "))
		strcat(memory, str);

	}
	fclose(fp);
}

void test() {

}
