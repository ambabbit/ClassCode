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
uint16_t MAR=0x0;
uint8_t ACC=0x0;
uint8_t memory[65536];

void readInMemory();
void printMemory();
void executeInstruction(void);
void fetchNextInstruction(void);
void printMemoryToFile(void);
void testPrint();

int main(void) {
	int count = 0;
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */
	readInMemory();

	while ((memory[PC] != HALT_OPCODE)){
		fetchNextInstruction();
		executeInstruction();
		count ++;
	}
	testPrint();
	printMemoryToFile();
	return 0;
}

void testPrint() {
	printf("\n ACC: %x\n", ACC);
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
		if ((IR %32)/16 == 0) {
		//True if method is address
		//else if true if operand is a constant
		if (IR %4 == 0) {
			incAmount += 2;
		} else if (IR%4 == 1) {
			//True if Register is ACC
			// else if Register is MAR
			if ((IR%8)/4 == 0) {
				incAmount += 1;
			} else {
				incAmount += 2;
			}
		}

		} else {
			incAmount += 2;
		}
	}
	printf("\nIncrement it %d\n", incAmount);
	PC += incAmount;
}

void executeInstruction(void) {
int instruction = IR;
int memoryAddress;
int mathBits = (instruction/16)-8;
bool MSB = instruction > 127;
bool branchBit = instruction > 15;
int address = (memory[PC-2] << 8) + memory [PC -1 ];
int destination, source;


switch (MSB) {
	case 1:

		//Examining Source
		if (instruction %4 == 0) {
			// Source is Indirect

			if (instruction%16 > 8) {
				// Destination is MAR so 16 bit

				source = (memory[MAR] <<8) +memory[MAR+1];
				printf("Indirect: %04x", source);

			} else {
				// Destination is ACC so 8 bit

				source = memory[MAR];
				printf("Indirect: %04x", source);
			}

			source = memory[MAR];

			printf("Source INDIRECT,");

		} else if (instruction %4 == 1) {

			printf("Source ACC,");

			source = ACC;

		} else if (instruction %4 == 2) {
			//Source is Constant

			if (instruction%16 > 8) {
				// Destination is MAR so 16 bit

				source = (memory[PC-2] <<8) +memory[PC-1];
				printf("Constant: %04x", source);

			} else {
				// Destination is ACC so 8 bit

				source = memory[PC-1];
				printf("Constant: %04x", source);
			}

		} else if (instruction %4 == 3) {
			// Source is Memory

			if (instruction%16 > 8) {
				// Destination is MAR so 16 bit
				source = (memory[MAR] <<8) +memory[MAR+1];
				printf("Memory: %04x", source);
			} else {
				// Destination is ACC so 8 bit
				source = memory[MAR];
				printf("Memory: %04x", source);
			}

			printf("[%x]", source);
		}


    //Examine Destination
		if (instruction%16 >= 12) {

			printf("MEMORY ");
			destination = (memory[PC-2]<<8) + memory[PC-1];
		} else if (instruction%16 >= 8) {
			printf(" dest MAR, ");
			destination = MAR;
		} else if (instruction%16 >= 4) {
			printf(" dest ACC, ");
			destination = ACC;
		} else {

			if (instruction%16 > 8) {
				// Destination is MAR so 16 bit
				destination = (memory[MAR] <<8) +memory[MAR+1];

				printf(" Indirect: %04x", source);

			} else {
				// Destination is ACC so 8 bit
				destination = memory[MAR];

				printf(" Indirect: %04x", source);
			}
			// Destination is Indirect

			destination = memory[MAR];
		}

    //do operations
		if (mathBits == 0) {
			printf("AND \n");
      destination &= source;
		} else if (mathBits == 1) {
			printf("OR \n");
      destination |= source;
		printf("dest: %x, source: %x", destination, source);
		// 1001 0011 ||
		// 0000 0011
		// 1001 0011
		} else if (mathBits == 2) {
			printf("XOR \n");
			printf("\n source %x", source);
      destination ^= source;
		} else if (mathBits == 3) {
			printf("ADD \n");
			printf("dest Val: %x, Source val %x", destination, source);
      destination += source;
		} else if (mathBits == 4) {
			printf("SUB \n");
      destination -= source;
		} else if (mathBits == 5) {
			printf("INC \n");
      destination++;
		} else if (mathBits == 6) {
			printf("DEC \n");
      destination--;
		} else if (mathBits == 7) {
			printf("NOT \n");
      destination ^= 0xff;
		}
    // store destination to where it needs

    //Examine Destination
		if (instruction%16 >= 12) {
      //@TODO Implement pull destination from memory
      memory[PC-1] = destination %256;
      memory[PC-2] = destination/256;
		} else if (instruction%16 >= 8) {
			MAR = destination;
		} else if (instruction%16 >= 4) {
			ACC = destination;
		} else {
			memory[MAR] = destination;
		}
		break;

	case 0:
		switch (branchBit) {

      // Branches or Jumping
      case 1:
          printf("branch type: ");

        	if (instruction%8 == 0) {
            PC = address;
            printf("BRA %x", address);
            // BRA (unconditional)
          } else if (instruction %8 == 1) {
            //BRZ
              printf("BRZ");
            if (ACC == 0) {
              //perform jump
              PC = address;
            }
          } else if (instruction %8 == 2) {
            if (ACC != 0) {
              PC = address;
              printf("BNE");
            }
          } else if (instruction % 8 == 3) {
            if (ACC < 0) {
              PC = address;
              printf("BLT");
            }
          } else if (instruction % 8 == 4) {
            if (ACC <= 0) {
              printf("BLE");
              PC = address;
            }
          } else if (instruction % 8 == 5) {
            if (ACC > 0) {
              printf("BGT");
            PC = address;
            }
          } else if (instruction % 8 == 6) {
            if (ACC >= 0) {
              printf("BGE");
              PC = address;
            }
          }
          break;
			case 0:
				// ------------------ Memory Operations------------------
				switch ((instruction%16)/8){
				case 0:
					// Store Section
					printf("STOR");

					// Find Register based on bit
					if ((instruction %8)/4 == 1) {

						// Register is MAR
						source = MAR;
						if (instruction%4 == 2) {
							memory[MAR] = source/256;
							memory[MAR+1] = source%256;
						} else {
							memoryAddress = (memory[PC-2] << 8) + memory[PC-1];
							memory[memoryAddress] = source/256;
							memory[memoryAddress+1] = source%256;
						}
						printf(" MAR");
					} else {

						// Register is ACC
						source = ACC;

						if (instruction%4 == 0) {

							memoryAddress = (memory[PC-2] << 8) + memory[PC-1];
							memory[memoryAddress] = source;

						} else if (instruction%4 == 1){

							memoryAddress = memory[PC-1] + memory[PC++];
							memory[memoryAddress] = source;

							printf(" Constant mem Address, %x ", PC);

						}else if (instruction%4 == 2) {
							memory[MAR] = source;
						}

						printf(" ACC");
					}
					printf(" [%04x]\n", memoryAddress);
					break;


				case 1:
					printf("LOAD");
//
					if (instruction%4 == 0) {
						memoryAddress = (memory[PC-2] << 8) + memory[PC-1];

						if ((instruction%8)/4 == 0) {
							source = memory[memoryAddress];
						} else {
							source = memoryAddress;
						}

					} else if (instruction%4 == 1) {

						if ((instruction%8)/4 == 0) {
							// Load Constant into ACC so 8 bit
							source = memory[PC-1];
						} else {
							//Load Constant into MAR so 16 bit
							memoryAddress = (memory[PC-2] << 8) + memory[PC-1];
							source = memoryAddress;
						}

					} else if (instruction%4 == 2) {

						source = memory[MAR];

					}


					if ((instruction%8)/4 == 0) {
						// Load to ACC
						ACC = source;
						if (instruction%4 == 0){
							printf(" ACC [%04x] is %02x\n", memoryAddress, source);
						} else {
							printf(" ACC 0x%02x\n", source);
						}

					} else {
						//Load to MAR
						MAR = source;
						printf(" MAR [%04x] is %04x\n", memoryAddress, source);
					}


					break;
				}
//				// Evaluate the Operand
//				if (instruction%4 == 0) {
//					// Operand is value at a memory location
//					memoryAddress = (memory[PC-2]<<8) + memory[PC-1];
//					source = memory[memoryAddress];
//
//				} else if (instruction%4 == 1) {
//					printf("CONST\n");
//					// Operand is a constant
//					if (instruction%8 > 3) {
//						// MAR is destination
//						source = (memory[PC-2]<<8) + memory[PC-1];
//
//					} else {
//						//ACC is destination for the constant
//						source = memory[PC-1];
//					}
//
//				} else {
//					// Operand indirect, address is saved in MAR
//					source = (memory[PC-2]<<8) + memory[PC-1];
//				}
//
//
//
//				// Determine Register
//				if (instruction%8 > 3) {
//					printf("INDEX ");
//					if (instruction > 7) {
//						MAR = source;
//					} else {
//
//					}
//
//				} else {
//					printf("ACC ");
//				}
//
//
//				if (instruction > 7)
//				{
//					printf("LOAD ");
//				} else {
//					printf("STOR ");
//				}

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
