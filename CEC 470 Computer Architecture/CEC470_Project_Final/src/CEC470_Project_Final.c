/*
 ============================================================================
 Name        : CEC470_Project_Final.c
 Author      : Adam
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */
#define HALT_OPCODE 0x19

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
void executeInstruction(void);
void fetchNextInstruction(void);
void printMemoryToFile(void);

int main(void) {
	int count = 0;
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */
	readInMemory();

	while ((memory[PC] != HALT_OPCODE) && (count < 5)){
		fetchNextInstruction();
		executeInstruction();
		count++;
	}

	printMemoryToFile();
	return 0;
}

void printMemory() {


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
	IR = memory[PC];
	int incAmount = 1;

	//true if math or logic operation
	if (IR > 127) {

		//true if destination is Memory
		if ((IR% 16)/4 == 0x3) {
			incAmount += 2;
		}

		//True if source is Memory
		//else if True if source is Constant
		if (IR%4 == 0x3) {
			incAmount+=2;
		} else if (IR%4 == 0x2) {
			//True if destination is ACC, increment 1 byte
			// Else if true if destination is MAR, increment 2 bytes
			if ((IR%16)/4 == 01) {
				incAmount++;
			} else if ((IR%16)/4 == 0x02 || (IR%16)/4 == 0x03){
				incAmount += 2;
			}
		}
	} else {
		//True if operand is address
		//else if true if operand is a constant
		if (IR %4 == 0) {
			incAmount += 2;
		} else if (IR%4 == 1) {
			//True if Register is ACC
			if ((IR%8)/4 == 0) {
				incAmount += 1;
			} else {
				incAmount += 2;
			}
		}
	}
	printf("\nIncrement it %d\n", incAmount);
	PC += incAmount;
}


void executeInstruction(void) {
int instruction = IR;
int memoryAddress;
int tempValue;
int mathBits = (instruction/16)-8;
bool MSB = instruction > 127;
bool branchBit = instruction > 15;
int address = (memory[PC+1] << 8) + memory [PC + 2];

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

		//Examining Source
		if (instruction %4 == 0) {
			tempValue = MAR;
			printf("MAR");
		} else if (instruction %4 == 1) {
			printf("ACC");
			tempValue = ACC;
		} else if (instruction %4 == 2) {
			//Source is Constant
			if (instruction%16 > 8) {
				// Destination is MAR so 16 bit
				tempValue = (memory[PC-2] <<8) +memory[PC-1];
				printf("[%04x]", tempValue);
			} else {
				// Destination is ACC so 8 bit
				tempValue = memory[PC-1];
				printf("[%04x]", tempValue);
			}

		} else if (instruction %4 == 3) {
			// Source is Memory
			tempValue = (memory[PC-2]<<8) + memory[PC-1];
			printf("[%x]", tempValue);
		}

		if (instruction%16 > 12) {
			printf("MEMORY ");

		} else if (instruction%16 > 8) {
			printf("MAR ");
			MAR = tempValue;
		} else if (instruction%16 > 4) {
			printf("ACC ");
			ACC= tempValue;
		} else {
			printf("Indirect");
			memory[MAR] = tempValue;
		}

		break;
	case 0:
		switch (branchBit) {
                // Branches or Jumping
                case 1:
                    printf("branch type: ");
                    // BRA (Unconditional branch 000)
                    printf("BRA");
                    PC = address;

                    if (ACC == 0) {
                        printf("BRZ");
                        PC = address;
                    }

                    else if (ACC != 0) {
                        printf("BNE");
                        PC = address;
                    }

                    else if (ACC < 0) {
                        printf("BLT");
                        PC = address;
                    }

                    else if (ACC <= 0) {
                        printf("BLE");
                        PC = address;
                    }

                    else if (ACC > 0) {
                        printf("BGT");
                        PC = address;
                    }

                    else if (ACC >= 0) {
                        printf("BGE");
                        PC = address;
                    }
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
					printf("ACC ");
				}

				if (instruction%4 == 0) {
					memoryAddress = (memory[PC-2]<<8) + memory[PC-1];
					printf("[%x], %02x \n", memoryAddress, memory[memoryAddress]);
				} else if (instruction%4 == 1) {
					printf("CONST\n");
					PC++;
				} else {
					printf("MAR");
				}
				break;
		}
	}

}

void printMemoryToFile(void) {
  FILE* outputFile = fopen("output.txt", "w");

  for (int i =0; i<sizeof(memory); i++) {

 	 fprintf(outputFile, " %02x", memory[i]);
     if(i %16 ==0) fprintf(outputFile, "\n");

  }

  fclose(outputFile);

}
