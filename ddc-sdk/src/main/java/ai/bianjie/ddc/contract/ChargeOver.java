package ai.bianjie.ddc.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class ChargeOver extends Contract {
    public static final String BINARY = "0x60a06040523060805234801561001457600080fd5b50600054610100900460ff168061002e575060005460ff16155b6100955760405162461bcd60e51b815260206004820152602e60248201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160448201526d191e481a5b9a5d1a585b1a5e995960921b606482015260840160405180910390fd5b600054610100900460ff161580156100b7576000805461ffff19166101011790555b80156100c9576000805461ff00191690555b506080516128be620000fb6000396000818161053501528181610575015281816106ab01526106eb01526128be6000f3fe60806040526004361061011e5760003560e01c8063715018a6116100a0578063c9c45a0f11610064578063c9c45a0f146102fb578063d213fe4514610330578063ef18e3c914610350578063f1e8b16814610370578063f2fde38b1461039057600080fd5b8063715018a6146102695780638129fc1c1461027e5780638a27a80d146102935780638da5cb5b146102b3578063c5837d82146102db57600080fd5b8063458c738e116100e7578063458c738e146101c95780634f1ef286146101f657806359dac58514610209578063635691891461022957806370a082311461024957600080fd5b80620b8f7014610123578063093f28e01461014557806318160ddd1461016557806336351c7c146101895780633659cfe6146101a9575b600080fd5b34801561012f57600080fd5b5061014361013e366004611ff4565b6103b0565b005b34801561015157600080fd5b50610143610160366004612027565b610433565b34801561017157600080fd5b5060c9545b6040519081526020015b60405180910390f35b34801561019557600080fd5b506101436101a436600461205a565b6104d0565b3480156101b557600080fd5b506101436101c4366004611ff4565b61052b565b3480156101d557600080fd5b506101e96101e4366004612161565b6105f3565b60405161018091906121d9565b610143610204366004612214565b6106a1565b34801561021557600080fd5b506101436102243660046122ae565b61075a565b34801561023557600080fd5b506101436102443660046122cb565b610829565b34801561025557600080fd5b50610176610264366004611ff4565b6108e9565b34801561027557600080fd5b5061014361092f565b34801561028a57600080fd5b50610143610965565b34801561029f57600080fd5b506101436102ae36600461205a565b6109e0565b3480156102bf57600080fd5b506033546040516001600160a01b039091168152602001610180565b3480156102e757600080fd5b506101436102f6366004611ff4565b610b01565b34801561030757600080fd5b5061031b610316366004612027565b610ba6565b60405163ffffffff9091168152602001610180565b34801561033c57600080fd5b5061014361034b36600461231b565b610c85565b34801561035c57600080fd5b5061014361036b366004612334565b610d23565b34801561037c57600080fd5b5061014361038b366004612370565b610e23565b34801561039c57600080fd5b506101436103ab366004611ff4565b610fcb565b6001600160a01b0381166103df5760405162461bcd60e51b81526004016103d69061242b565b60405180910390fd5b6103e7611063565b6001600160a01b038116600081815260ca6020526040808220600101805460ff19169055517f0ba05d508af447342f624920545278b6e2d2320ee40cb9eff56b89d21e1b25e89190a250565b6001600160a01b0382166104595760405162461bcd60e51b81526004016103d69061242b565b610461611063565b6001600160a01b038216600081815260ca602090815260408083206001600160e01b0319861680855290835292819020805463ffffffff19169055519182527f2f93e067617701594eddb2443d90441c5bb959df555ae8e4d150f0a8bf8b006d91015b60405180910390a25050565b6104db338383611130565b6104e633838361129c565b6040518181526001600160a01b0383169033907f4ade20d82044693c0d3331ba1d2a482d103734f274166989491c8d30d3f2ecb1906020015b60405180910390a35050565b6001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001630036105735760405162461bcd60e51b81526004016103d690612458565b7f00000000000000000000000000000000000000000000000000000000000000006001600160a01b03166105a561138e565b6001600160a01b0316146105cb5760405162461bcd60e51b81526004016103d6906124a4565b6105d4816113bc565b604080516000808252602082019092526105f0918391906113e6565b50565b60606000825167ffffffffffffffff81111561061157610611612084565b60405190808252806020026020018201604052801561063a578160200160208202803683370190505b50905060005b835181101561069a5761066b84828151811061065e5761065e6124f0565b60200260200101516108e9565b82828151811061067d5761067d6124f0565b6020908102919091010152806106928161251c565b915050610640565b5092915050565b6001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001630036106e95760405162461bcd60e51b81526004016103d690612458565b7f00000000000000000000000000000000000000000000000000000000000000006001600160a01b031661071b61138e565b6001600160a01b0316146107415760405162461bcd60e51b81526004016103d6906124a4565b61074a826113bc565b610756828260016113e6565b5050565b610762611063565b61076b33611531565b60cc54600160a01b900460ff161515811515036107ca5760405162461bcd60e51b815260206004820152601860248201527f6368617267653a696e76616c6964206f7065726174696f6e000000000000000060448201526064016103d6565b60cc805460ff60a01b1916600160a01b83151502179055336001600160a01b03167f7d50bb020f29cc2d35f133362054f432834ddc2cfc3f6dc0f9e9dea5bf8ffe6b8260405161081e911515815260200190565b60405180910390a250565b6001600160a01b03831661084f5760405162461bcd60e51b81526004016103d69061242b565b610857611063565b6001600160a01b038316600081815260ca602090815260408083206001808201805460ff191690911790556001600160e01b0319871680855290835292819020805463ffffffff191663ffffffff87169081179091558151938452918301919091527f929dc21675623ba3d42ef9e085962b7d88bf57ba159fe8f0a86d6785195e2acc910160405180910390a2505050565b60006001600160a01b03821681036109135760405162461bcd60e51b81526004016103d69061242b565b506001600160a01b0316600090815260cb602052604090205490565b6033546001600160a01b031633146109595760405162461bcd60e51b81526004016103d690612535565b6109636000611611565b565b600054610100900460ff168061097e575060005460ff16155b61099a5760405162461bcd60e51b81526004016103d69061256a565b600054610100900460ff161580156109bc576000805461ffff19166101011790555b6109c4611663565b6109cc6116ca565b80156105f0576000805461ff001916905550565b80600003610a305760405162461bcd60e51b815260206004820181905260248201527f6368617267653a206e6f207472616e73666572206973206e656365737361727960448201526064016103d6565b6001600160a01b0382163b15158015610a6457506001600160a01b038216600090815260ca602052604090206001015460ff165b610ab05760405162461bcd60e51b815260206004820152601860248201527f6368617267653a206e6f742044444320636f6e7472616374000000000000000060448201526064016103d6565b610ab8611063565b610ac482335b8361129c565b6040518181526001600160a01b0383169033907fca2ce982d63c71a419517d389df253be4b0d6f4da85fe1491e49608b139ee0be9060200161051f565b6033546001600160a01b03163314610b2b5760405162461bcd60e51b81526004016103d690612535565b6001600160a01b038116600003610b845760405162461bcd60e51b815260206004820152601d60248201527f6368617267653a206175746820746865207a65726f206164647265737300000060448201526064016103d6565b60cc80546001600160a01b0319166001600160a01b0392909216919091179055565b60006001600160a01b0383168103610bd05760405162461bcd60e51b81526004016103d69061242b565b6001600160a01b038316600090815260ca602052604090206001015460ff16610c495760405162461bcd60e51b815260206004820152602560248201527f6368617267653a6464632070726f787920636f6e747261637420756e617661696044820152646c61626c6560d81b60648201526084016103d6565b506001600160a01b038216600090815260ca602090815260408083206001600160e01b03198516845290915290205463ffffffff165b92915050565b80600003610cd55760405162461bcd60e51b815260206004820181905260248201527f6368617267653a206e6f207472616e73666572206973206e656365737361727960448201526064016103d6565b610cdd611063565b610ce8600033610abe565b60405181815233906000907f4ade20d82044693c0d3331ba1d2a482d103734f274166989491c8d30d3f2ecb19060200160405180910390a350565b6001600160a01b038316610d495760405162461bcd60e51b81526004016103d69061242b565b80600003610d905760405162461bcd60e51b815260206004820152601460248201527318da185c99d94e9a5b9d985b1a5908191918d25960621b60448201526064016103d6565b336000610d9d8285610ba6565b905063ffffffff811615610dbc57610dbc85838363ffffffff1661129c565b604080516001600160e01b03198616815263ffffffff831660208201529081018490526001600160a01b0380841691908716907f750e56f33a72767cd99db8943f4d04ca416c55fb783003107a869f5d5062dbab9060600160405180910390a35050505050565b610e2b611731565b815115801590610e3b5750805115155b610ead5760405162461bcd60e51b815260206004820152603860248201527f6368617267653a746f4c69737420616e6420616d6f756e7473206c656e67746860448201527f206d7573742062652067726561746572207468616e20302e000000000000000060648201526084016103d6565b8051825114610ef75760405162461bcd60e51b81526020600482015260166024820152750c6d0c2e4ceca74d8cadccee8d040dad2e6dac2e8c6d60531b60448201526064016103d6565b60005b8251811015610f8f57610f4033848381518110610f1957610f196124f0565b6020026020010151848481518110610f3357610f336124f0565b6020026020010151611130565b610f7d33848381518110610f5657610f566124f0565b6020026020010151848481518110610f7057610f706124f0565b602002602001015161129c565b80610f878161251c565b915050610efa565b50336001600160a01b03167f744c96b20cd1add6104a750930b0e49658e6c5542ed4e9c9a90ae33359ceb8f983836040516104c49291906125b8565b6033546001600160a01b03163314610ff55760405162461bcd60e51b81526004016103d690612535565b6001600160a01b03811661105a5760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084016103d6565b6105f081611611565b60cc546001600160a01b031663ed5cad643360006040518363ffffffff1660e01b815260040161109492919061262e565b602060405180830381865afa1580156110b1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906110d59190612669565b6109635760405162461bcd60e51b815260206004820152602660248201527f6368617267653a6e6f742061206f70657261746f7220726f6c65206f722064696044820152651cd8589b195960d21b60648201526084016103d6565b6001600160a01b03821661117d5760405162461bcd60e51b81526020600482015260146024820152736368617267653a207a65726f206164647265737360601b60448201526064016103d6565b816001600160a01b0316836001600160a01b0316036111e95760405162461bcd60e51b815260206004820152602260248201527f6368617267653a20696e76616c6964207265636861726765206f70657261746960448201526137b760f11b60648201526084016103d6565b806000036112325760405162461bcd60e51b815260206004820152601660248201527518da185c99d94e881a5b9d985b1a5908185b5bdd5b9d60521b60448201526064016103d6565b61123c8383611796565b6112975760405162461bcd60e51b815260206004820152602660248201527f6368617267653a20756e737570706f72746564207265636861726765206f70656044820152653930ba34b7b760d11b60648201526084016103d6565b505050565b6001600160a01b0383161561134457806112b5846108e9565b10156113115760405162461bcd60e51b815260206004820152602560248201527f6368617267653a206163636f756e742062616c616e6365206973206e6f7420656044820152640dcdeeaced60db1b60648201526084016103d6565b6001600160a01b038316600090815260cb602052604081208054839290611339908490612686565b9091555061135c9050565b8060c960008282546113569190612699565b90915550505b6001600160a01b038216600090815260cb602052604081208054839290611384908490612699565b9091555050505050565b7f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc546001600160a01b031690565b6033546001600160a01b031633146105f05760405162461bcd60e51b81526004016103d690612535565b60006113f061138e565b90506113fb84611c5d565b6000835111806114085750815b15611419576114178484611d02565b505b7f4910fdfa16fed3260ed0e7147f7cc6da11a60208b5b9406d12a635614ffd9143805460ff1661152a57805460ff191660011781556040516001600160a01b038316602482015261149890869060440160408051601f198184030181529190526020810180516001600160e01b0316631b2ce7f360e11b179052611d02565b50805460ff191681556114a961138e565b6001600160a01b0316826001600160a01b0316146115215760405162461bcd60e51b815260206004820152602f60248201527f45524331393637557067726164653a207570677261646520627265616b73206660448201526e75727468657220757067726164657360881b60648201526084016103d6565b61152a85611ded565b5050505050565b6001600160a01b0381166115575760405162461bcd60e51b81526004016103d69061242b565b60cc546040516395c2a8d960e01b81526001600160a01b038381166004830152909116906395c2a8d990602401602060405180830381865afa1580156115a1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906115c59190612669565b6105f05760405162461bcd60e51b815260206004820152601e60248201527f6368617267653a6e6f74206120617661696c61626c65206163636f756e74000060448201526064016103d6565b603380546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b600054610100900460ff168061167c575060005460ff16155b6116985760405162461bcd60e51b81526004016103d69061256a565b600054610100900460ff161580156116ba576000805461ffff19166101011790555b6116c2611e2d565b6109cc611e97565b600054610100900460ff16806116e3575060005460ff16155b6116ff5760405162461bcd60e51b81526004016103d69061256a565b600054610100900460ff16158015611721576000805461ffff19166101011790555b611729611e2d565b6109cc611e2d565b60cc54600160a01b900460ff166109635760405162461bcd60e51b815260206004820152602360248201527f6368617267653a7377697463686572206f6620626174636820697320636c6f7360448201526232b21760e91b60648201526084016103d6565b60006117d66040805160e0810182526060808252602082015290810160008152606060208201526040016000815260200160008152602001606081525090565b60cc5460405163fbcbc0f160e01b81526001600160a01b0386811660048301529091169063fbcbc0f190602401600060405180830381865afa158015611820573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526118489190810190612733565b5092935090918560408101606082016080830160a0840185600181111561187157611871612618565b600181111561188257611882612618565b905285600181111561189657611896612618565b60018111156118a7576118a7612618565b90528590528560028111156118be576118be612618565b60028111156118cf576118cf612618565b90529490945250600192506118e2915050565b816080015160018111156118f8576118f8612618565b14801561191a575060018160a00151600181111561191857611918612618565b145b6119665760405162461bcd60e51b815260206004820152601860248201527f6368617267653a206066726f6d602069732066726f7a656e000000000000000060448201526064016103d6565b60028160400151600281111561197e5761197e612618565b036119cb5760405162461bcd60e51b815260206004820152601e60248201527f6368617267653a206e6f207265636861726765207065726d697373696f6e000060448201526064016103d6565b611a096040805160e0810182526060808252602082015290810160008152606060208201526040016000815260200160008152602001606081525090565b60cc5460405163fbcbc0f160e01b81526001600160a01b0386811660048301529091169063fbcbc0f190602401600060405180830381865afa158015611a53573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052611a7b9190810190612733565b5092935090918560408101606082016080830160a08401856001811115611aa457611aa4612618565b6001811115611ab557611ab5612618565b9052856001811115611ac957611ac9612618565b6001811115611ada57611ada612618565b9052859052856002811115611af157611af1612618565b6002811115611b0257611b02612618565b9052949094525060019250611b15915050565b81608001516001811115611b2b57611b2b612618565b148015611b4d575060018160a001516001811115611b4b57611b4b612618565b145b611b925760405162461bcd60e51b815260206004820152601660248201527531b430b933b29d10303a37b01034b990333937bd32b760511b60448201526064016103d6565b600282604001516002811115611baa57611baa612618565b148015611bcc5750600281604001516002811115611bca57611bca612618565b145b80611c225750600182604001516002811115611bea57611bea612618565b148015611c0c5750600181604001516002811115611c0a57611c0a612618565b145b8015611c22575080518251611c2091611ef7565b155b80611c425750600081604001516002811115611c4057611c40612618565b145b15611c5257600092505050610c7f565b506001949350505050565b803b611cc15760405162461bcd60e51b815260206004820152602d60248201527f455243313936373a206e657720696d706c656d656e746174696f6e206973206e60448201526c1bdd08184818dbdb9d1c9858dd609a1b60648201526084016103d6565b7f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc80546001600160a01b0319166001600160a01b0392909216919091179055565b6060823b611d615760405162461bcd60e51b815260206004820152602660248201527f416464726573733a2064656c65676174652063616c6c20746f206e6f6e2d636f6044820152651b9d1c9858dd60d21b60648201526084016103d6565b600080846001600160a01b031684604051611d7c9190612812565b600060405180830381855af49150503d8060008114611db7576040519150601f19603f3d011682016040523d82523d6000602084013e611dbc565b606091505b5091509150611de4828260405180606001604052806027815260200161286260279139611f98565b95945050505050565b611df681611c5d565b6040516001600160a01b038216907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a250565b600054610100900460ff1680611e46575060005460ff16155b611e625760405162461bcd60e51b81526004016103d69061256a565b600054610100900460ff161580156109cc576000805461ffff191661010117905580156105f0576000805461ff001916905550565b600054610100900460ff1680611eb0575060005460ff16155b611ecc5760405162461bcd60e51b81526004016103d69061256a565b600054610100900460ff16158015611eee576000805461ffff19166101011790555b6109cc33611611565b805182516000918491849114611f1257600092505050610c7f565b815160005b81811015611f8b57828181518110611f3157611f316124f0565b602001015160f81c60f81b6001600160f81b031916848281518110611f5857611f586124f0565b01602001516001600160f81b03191614611f79576000945050505050610c7f565b80611f838161251c565b915050611f17565b5060019695505050505050565b60608315611fa7575081611fd1565b825115611fb75782518084602001fd5b8160405162461bcd60e51b81526004016103d6919061282e565b9392505050565b80356001600160a01b0381168114611fef57600080fd5b919050565b60006020828403121561200657600080fd5b611fd182611fd8565b80356001600160e01b031981168114611fef57600080fd5b6000806040838503121561203a57600080fd5b61204383611fd8565b91506120516020840161200f565b90509250929050565b6000806040838503121561206d57600080fd5b61207683611fd8565b946020939093013593505050565b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff811182821017156120c3576120c3612084565b604052919050565b600067ffffffffffffffff8211156120e5576120e5612084565b5060051b60200190565b600082601f83011261210057600080fd5b81356020612115612110836120cb565b61209a565b82815260059290921b8401810191818101908684111561213457600080fd5b8286015b848110156121565761214981611fd8565b8352918301918301612138565b509695505050505050565b60006020828403121561217357600080fd5b813567ffffffffffffffff81111561218a57600080fd5b612196848285016120ef565b949350505050565b600081518084526020808501945080840160005b838110156121ce578151875295820195908201906001016121b2565b509495945050505050565b602081526000611fd1602083018461219e565b600067ffffffffffffffff82111561220657612206612084565b50601f01601f191660200190565b6000806040838503121561222757600080fd5b61223083611fd8565b9150602083013567ffffffffffffffff81111561224c57600080fd5b8301601f8101851361225d57600080fd5b803561226b612110826121ec565b81815286602083850101111561228057600080fd5b816020840160208301376000602083830101528093505050509250929050565b80151581146105f057600080fd5b6000602082840312156122c057600080fd5b8135611fd1816122a0565b6000806000606084860312156122e057600080fd5b6122e984611fd8565b92506122f76020850161200f565b9150604084013563ffffffff8116811461231057600080fd5b809150509250925092565b60006020828403121561232d57600080fd5b5035919050565b60008060006060848603121561234957600080fd5b61235284611fd8565b92506123606020850161200f565b9150604084013590509250925092565b6000806040838503121561238357600080fd5b823567ffffffffffffffff8082111561239b57600080fd5b6123a7868387016120ef565b93506020915081850135818111156123be57600080fd5b85019050601f810186136123d157600080fd5b80356123df612110826120cb565b81815260059190911b820183019083810190888311156123fe57600080fd5b928401925b8284101561241c57833582529284019290840190612403565b80955050505050509250929050565b6020808252601390820152726368617267653a7a65726f206164647265737360681b604082015260600190565b6020808252602c908201527f46756e6374696f6e206d7573742062652063616c6c6564207468726f7567682060408201526b19195b1959d85d1958d85b1b60a21b606082015260800190565b6020808252602c908201527f46756e6374696f6e206d7573742062652063616c6c6564207468726f7567682060408201526b6163746976652070726f787960a01b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b60006001820161252e5761252e612506565b5060010190565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b6020808252602e908201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160408201526d191e481a5b9a5d1a585b1a5e995960921b606082015260800190565b604080825283519082018190526000906020906060840190828701845b828110156125fa5781516001600160a01b0316845292840192908401906001016125d5565b5050508381038285015261260e818661219e565b9695505050505050565b634e487b7160e01b600052602160045260246000fd5b6001600160a01b0383168152604081016003831061265c57634e487b7160e01b600052602160045260246000fd5b8260208301529392505050565b60006020828403121561267b57600080fd5b8151611fd1816122a0565b81810381811115610c7f57610c7f612506565b80820180821115610c7f57610c7f612506565b60005b838110156126c75781810151838201526020016126af565b50506000910152565b600082601f8301126126e157600080fd5b81516126ef612110826121ec565b81815284602083860101111561270457600080fd5b6121968260208301602087016126ac565b805160038110611fef57600080fd5b805160028110611fef57600080fd5b600080600080600080600060e0888a03121561274e57600080fd5b875167ffffffffffffffff8082111561276657600080fd5b6127728b838c016126d0565b985060208a015191508082111561278857600080fd5b6127948b838c016126d0565b97506127a260408b01612715565b965060608a01519150808211156127b857600080fd5b6127c48b838c016126d0565b95506127d260808b01612724565b94506127e060a08b01612724565b935060c08a01519150808211156127f657600080fd5b506128038a828b016126d0565b91505092959891949750929550565b600082516128248184602087016126ac565b9190910192915050565b602081526000825180602084015261284d8160408501602087016126ac565b601f01601f1916919091016040019291505056fe416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c6564a264697066735822122022a413b332ad0785e98a696aae17a9b9981392087e2b97ca4cf8f8de4dfc9c6764736f6c63430008110033";

    public static final Event ADMINCHANGED_EVENT = new Event("AdminChanged",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Address>() {
            }));
    ;

    public static final Event BEACONUPGRADED_EVENT = new Event("BeaconUpgraded",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }));
    ;

    public static final Event DELDDC_EVENT = new Event("DelDDC",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }));
    ;

    public static final Event DELFEE_EVENT = new Event("DelFee",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Bytes4>() {
            }));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    public static final Event PAY_EVENT = new Event("Pay",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Bytes4>() {
            }, new TypeReference<Uint32>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event RECHARGE_EVENT = new Event("Recharge",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event RECHARGEBATCH_EVENT = new Event("RechargeBatch",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<DynamicArray<Address>>() {
            }, new TypeReference<DynamicArray<Uint256>>() {
            }));
    ;

    public static final Event SETFEE_EVENT = new Event("SetFee",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Bytes4>() {
            }, new TypeReference<Uint32>() {
            }));
    ;

    public static final Event SETSWITCHERSTATEOFBATCH_EVENT = new Event("SetSwitcherStateOfBatch",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Bool>() {
            }));
    ;

    public static final Event SETTLEMENT_EVENT = new Event("Settlement",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event UPGRADED_EVENT = new Event("Upgraded",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }));
    ;

    protected ChargeOver(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    public Charge.AdminChangedEventResponse adminChangedEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(ADMINCHANGED_EVENT, log);
        Charge.AdminChangedEventResponse typedResponse = new Charge.AdminChangedEventResponse();
        typedResponse.log = log;
        typedResponse.previousAdmin = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.newAdmin = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Charge.BeaconUpgradedEventResponse beaconUpgradedEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(BEACONUPGRADED_EVENT, log);
        Charge.BeaconUpgradedEventResponse typedResponse = new Charge.BeaconUpgradedEventResponse();
        typedResponse.log = log;
        typedResponse.beacon = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Charge.DelDDCEventResponse delDDCEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(DELDDC_EVENT, log);
        Charge.DelDDCEventResponse typedResponse = new Charge.DelDDCEventResponse();
        typedResponse.log = log;
        typedResponse.ddcAddr = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Charge.DelFeeEventResponse delFeeEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(DELFEE_EVENT, log);
        Charge.DelFeeEventResponse typedResponse = new Charge.DelFeeEventResponse();
        typedResponse.log = log;
        typedResponse.ddcAddr = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.sig = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Charge.OwnershipTransferredEventResponse ownershipTransferredEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        Charge.OwnershipTransferredEventResponse typedResponse = new Charge.OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Charge.PayEventResponse payEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(PAY_EVENT, log);
        Charge.PayEventResponse typedResponse = new Charge.PayEventResponse();
        typedResponse.log = log;
        typedResponse.payer = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.payee = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.sig = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.ddcId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Charge.RechargeEventResponse rechargeEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(RECHARGE_EVENT, log);
        Charge.RechargeEventResponse typedResponse = new Charge.RechargeEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Charge.RechargeBatchEventResponse rechargeBatchEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(RECHARGEBATCH_EVENT, log);
        Charge.RechargeBatchEventResponse typedResponse = new Charge.RechargeBatchEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.toList = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amounts = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Charge.SetFeeEventResponse setFeeEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(SETFEE_EVENT, log);
        Charge.SetFeeEventResponse typedResponse = new Charge.SetFeeEventResponse();
        typedResponse.log = log;
        typedResponse.ddcAddr = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.sig = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Charge.SettlementEventResponse settlementEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(SETTLEMENT_EVENT, log);
        Charge.SettlementEventResponse typedResponse = new Charge.SettlementEventResponse();
        typedResponse.log = log;
        typedResponse.accAddr = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.ddcAddr = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Charge.UpgradedEventResponse upgradedEventFlowable(Log log) {
        EventValuesWithLog eventValues = extractEventParametersWithLog(UPGRADED_EVENT, log);
        Charge.UpgradedEventResponse typedResponse = new Charge.UpgradedEventResponse();
        typedResponse.log = log;
        typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public static ChargeOver load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ChargeOver(contractAddress, web3j, credentials, contractGasProvider);
    }
}
