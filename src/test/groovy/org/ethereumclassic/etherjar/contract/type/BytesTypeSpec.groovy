package org.ethereumclassic.etherjar.contract.type

import org.ethereumclassic.etherjar.model.Hex32
import org.ethereumclassic.etherjar.model.HexData
import spock.lang.Specification

class BytesTypeSpec extends Specification {

    final static DEFAULT_TYPE = [] as BytesType

    def "should parse string representation"() {
        when:
        def opt = BytesType.from 'bytes'

        then:
        opt.present
    }

    def "should detect null string representation"() {
        when:
        BytesType.from null

        then:
        thrown NullPointerException
    }

    def "should ignore empty string representation"() {
        when:
        def opt = BytesType.from ''

        then:
        !opt.present
    }

    def "should ignore wrong string representation"() {
        when:
        def opt = BytesType.from input

        then:
        !opt.present

        where:
        _ | input
        _ | 'uint40'
        _ | 'int256'
    }

    def "should return a canonical string representation" () {
        expect:
        DEFAULT_TYPE.canonicalName == 'bytes'
    }

    def "should encode & decode array of bytes"() {
        def obj = bytes as byte[]

        when:
        def data = DEFAULT_TYPE.encode obj
        def res = DEFAULT_TYPE.decode data

        then:
        data == hex
        Arrays.equals res, obj

        where:
        bytes                       | hex
        [0x37]                      | Type.encodeLength(1).concat(HexData.from('0x3700000000000000000000000000000000000000000000000000000000000000'))
        [0x64, 0x61, 0x76, 0x65]    | Type.encodeLength(4).concat(HexData.from('0x6461766500000000000000000000000000000000000000000000000000000000'))
        [0x12] * 123                | Type.encodeLength(123).concat(HexData.from('0x' + '12' * 123 + '00' * 5))
        [0x12] * 32                 | Type.encodeLength(32).concat(HexData.from('0x' + '12' * 32))
        []                          | Type.encodeLength(0)
    }

    def "should catch wrong data to decode"() {
        when:
        DEFAULT_TYPE.decode hex

        then:
        thrown IllegalArgumentException

        where:
        _ | hex
        _ | Type.encodeLength(1)
        _ | Type.encodeLength(32).concat(Hex32.EMPTY, Hex32.EMPTY)
        _ | Type.encodeLength(34).concat(HexData.from('0x6461766500000000000000000000000000000000000000000000000000000000'))
        _ | Type.encodeLength(0).concat(Hex32.EMPTY)
    }

    def "should catch empty data to decode"() {
        when:
        DEFAULT_TYPE.decode(HexData.EMPTY)

        then:
        thrown IllegalArgumentException
    }

    def "should calculate consistent hashcode"() {
        expect:
        first.hashCode() == second.hashCode()

        where:
        first           | second
        DEFAULT_TYPE    | [] as BytesType
    }

    def "should be equal"() {
        expect:
        first == second

        where:
        first           | second
        DEFAULT_TYPE    | DEFAULT_TYPE
        DEFAULT_TYPE    | [] as BytesType
    }

    def "should not be equal"() {
        expect:
        first != second

        where:
        first           | second
        DEFAULT_TYPE    | null
        DEFAULT_TYPE    | 'ABC'
        DEFAULT_TYPE    | new UIntType()
    }

    def "should be converted to a string representation"() {
        expect:
        DEFAULT_TYPE as String == 'bytes'
    }
}