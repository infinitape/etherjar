/*
 * Copyright (c) 2016-2019 Igor Artamonov, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.infinitape.etherjar.rpc

import io.infinitape.etherjar.domain.TransactionId
import spock.lang.Specification

class ParityCommandsSpec extends Specification {

    def trace() {
        when:
        def call = Commands.parity().traceTransaction(TransactionId.from("0x6013af0b08e2f0e8dacdfcf482813c7e1898ec2b3cd21da6d2c16ce9899a2d27"))

        then:
        call.method == "trace_transaction"
        call.params == ['0x6013af0b08e2f0e8dacdfcf482813c7e1898ec2b3cd21da6d2c16ce9899a2d27']
        call.jsonType == TraceList
        call.resultType == TraceList
    }
}
