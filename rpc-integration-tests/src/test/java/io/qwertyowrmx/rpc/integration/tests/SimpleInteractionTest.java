/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowrmx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.qwertyowrmx.rpc.integration.tests;

import io.qwertyowrmx.rpc.client.StubFactory;
import io.qwertyowrmx.rpc.integration.tests.utils.RpcServerOperations;
import io.qwertyowrmx.rpc.integration.tests.utils.SimpleServiceContract;
import io.qwertyowrmx.rpc.integration.tests.utils.provider.SimpleArgsProvider;
import io.qwertyowrmx.rpc.server.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class SimpleInteractionTest implements RpcServerOperations {

    @ArgumentsSource(SimpleArgsProvider.class)
    @ParameterizedTest
    public void testCallRemoteMethods(RpcServer server,
                                      StubFactory<SimpleServiceContract> builder) {

        new Thread(server::start).start();
        waitForServerToStart(server);

        SimpleServiceContract service = builder.createStub();

        assertEquals(10, service.add(5, 5));
        assertEquals(30, service.add(12, 18));
        assertEquals(5, service.add(-10, 15));
        assertEquals(40, service.multiply(4, 10));
        assertEquals(100, service.multiply(20, 5));

        server.shutdown();

        waitForServerToStop(server);
    }
}
