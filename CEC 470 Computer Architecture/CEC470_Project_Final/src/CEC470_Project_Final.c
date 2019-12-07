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
#include <string.h>

uint16_t PC=0;
uint8_t IR=0;
uint16_t MAR=0x0;
uint8_t ACC=0x0;
uint8_t memory[65536];
uint8_t t_mem[65536];

void readInMemory();
void executeInstruction(void);
void fetchNextInstruction(void);
void printMemoryToFile(void);

int main(void) {
	int count = 0;
	readInMemory();

	while (memory[PC] != HALT_OPCODE){
		fetchNextInstruction();
		executeInstruction();
		count ++;
	}

	printMemoryToFile();
	return 0;
}


void readInMemory() {
	int count = 0;
	char str[255];
	FILE* fp = fopen("mem_in.txt", "r");
	if (!fp) {printf("EMPTY %s", str);}

	while(fgets(str, 4,fp)!= NULL) {
		int readInValue = (int) strtol(str,NULL,16);

		if (strcmp(str, "") != 0 && strcmp(str, "\n") != 0 && strcmp(str, "\r") != 0 && strcmp(str, "\0") != 0) {
			memory[count++] = readInValue;
		}
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

			} else {
				// Destination is ACC so 8 bit

				source = memory[MAR];
			}

			source = memory[MAR];

		} else if (instruction %4 == 1) {

			source = ACC;

		} else if (instruction %4 == 2) {
			//Source is Constant

			if (instruction%16 > 8) {
				// Destination is MAR so 16 bit

				source = (memory[PC-2] <<8) +memory[PC-1];

			} else {
				// Destination is ACC so 8 bit

				source = memory[PC-1];
			}

		} else if (instruction %4 == 3) {
			// Source is Memory

			if (instruction%16 > 8) {
				// Destination is MAR so 16 bit
				source = (memory[(memory[PC-2] <<8) +memory[PC-1]] <<8) + memory[((memory[PC-2] <<8) +memory[PC-1]) +1];
			} else {
				// Destination is ACC so 8 bit
				source = memory[(memory[PC-2] <<8) +memory[PC-1]];
			}

		}


    //Examine Destination
		if (instruction%16 >= 12) {
			destination = (memory[PC-2]<<8) + memory[PC-1];
		} else if (instruction%16 >= 8) {
			destination = MAR;
		} else if (instruction%16 >= 4) {
			destination = ACC;
		} else {

			if (instruction%16 > 8) {
				// Destination is MAR so 16 bit
				destination = (memory[MAR] <<8) +memory[MAR+1];

			} else {
				// Destination is ACC so 8 bit
				destination = memory[MAR];

			}
			// Destination is Indirect

			destination = memory[MAR];
		}

    //do operations
		if (mathBits == 0) {
      destination &= source;
		} else if (mathBits == 1) {
      destination |= source;
		} else if (mathBits == 2) {
      destination ^= source;
		} else if (mathBits == 3) {
      destination += source;
		} else if (mathBits == 4) {
      destination -= source;
		} else if (mathBits == 5) {
      destination++;
		} else if (mathBits == 6) {
      destination--;
		} else if (mathBits == 7) {
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

        	if (instruction%8 == 0) {
            PC = address;
            // BRA (unconditional)
          } else if (instruction %8 == 1) {
            //BRZ
            if (ACC == 0) {
              //perform jump
              PC = address;
            }
          } else if (instruction %8 == 2) {
            if (ACC != 0) {
              PC = address;
            }
          } else if (instruction % 8 == 3) {
            if (ACC < 0) {
              PC = address;
            }
          } else if (instruction % 8 == 4) {
            if (ACC <= 0) {
              printf("BLE");
              PC = address;
            }
          } else if (instruction % 8 == 5) {
            if (ACC > 0) {
            PC = address;
            }
          } else if (instruction % 8 == 6) {
            if (ACC >= 0) {

              PC = address;
            }
          }
          break;
			case 0:
				// ------------------ Memory Operations------------------
				switch ((instruction%16)/8){
				case 0:
					// Store Section

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
					} else {

						// Register is ACC
						source = ACC;

						if (instruction%4 == 0) {

							memoryAddress = (memory[PC-2] << 8) + memory[PC-1];
							memory[memoryAddress] = source;

						} else if (instruction%4 == 1){

							memoryAddress = memory[PC-1] + memory[PC++];
							memory[memoryAddress] = source;


						}else if (instruction%4 == 2) {
							memory[MAR] = source;
						}

					}
					break;


				case 1:
//
					if (instruction%4 == 0) {
						//operand  is address
						memoryAddress = (memory[PC-2] << 8) + memory[PC-1];

						if ((instruction%8)/4 == 0) {
							// Register is ACC
							source = memory[memoryAddress];
						} else {
							source = (memory[memoryAddress] << 8) + memory[memoryAddress +1];

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
						} else {
						}

					} else {
						//Load to MAR
						MAR = source;
					}


					break;
				}

				break;
		}
	}
}

void printMemoryToFile(void) {
  FILE* outputFile = fopen("output.txt", "w");

  for (int i =1; i<=sizeof(memory); i++) {

 	 fprintf(outputFile, " %02x", memory[i-1]);
     if(i %16 ==0) fprintf(outputFile, "\n");

  }

  fclose(outputFile);

}
